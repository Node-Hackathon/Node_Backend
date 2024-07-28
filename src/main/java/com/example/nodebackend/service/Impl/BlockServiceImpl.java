package com.example.nodebackend.service.Impl;

import com.example.nodebackend.S3.S3Uploader;
import com.example.nodebackend.data.dao.BlockDao;
import com.example.nodebackend.data.dto.BlockDto.BlockDto;
import com.example.nodebackend.data.dto.BlockDto.BlockResponseDto;
import com.example.nodebackend.data.entity.Block;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.BlockRepository;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.service.BlockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlockServiceImpl implements BlockService {

    private final S3Uploader s3Uploader;
    private final JwtProvider jwtProvider;
    private final BlockRepository blockRepository;
    private final UserRepository userRepository;
    private final WebClient webClient;
    private final BlockDao blockDao;

    private final Logger logger = LoggerFactory.getLogger(BlockResponseDto.class);

    public BlockServiceImpl(S3Uploader s3Uploader, JwtProvider jwtProvider, BlockRepository blockRepository, WebClient.Builder webClientBuilder, BlockDao blockDao,UserRepository userRepository) {
        this.s3Uploader = s3Uploader;
        this.jwtProvider = jwtProvider;
        this.blockRepository = blockRepository;
        this.webClient = webClientBuilder.baseUrl("http://43.203.212.168:8000").build();
        this.blockDao = blockDao;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public BlockResponseDto BlockStacking(MultipartFile blockImage, HttpServletRequest request) throws IOException {
        BlockResponseDto blockResponseDto = null;

        try {
            String imageUrl = s3Uploader.uploadImage(blockImage, "images/block");
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("image_url", imageUrl);

            Mono<BlockResponseDto> response = webClient.post()
                    .uri("/predict_image")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(BlockResponseDto.class);

            blockResponseDto = response.block(); // blocking call
            logger.info("Received Response: {}", blockResponseDto);

            if (blockResponseDto != null) {
                String info = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
                User user = userRepository.findByPhoneNum(info);

                Block block = new Block();
                block.setCombination_name(blockResponseDto.getResults().stream().map(BlockDto::getName).collect(Collectors.toList()).toString());
                block.setAccuracy(blockResponseDto.getResults().stream().map(BlockDto::getAccuracy).collect(Collectors.toList()).toString());
                block.setImage_Url(blockResponseDto.getImageUrl());
                block.setUser(user);
                block.setCount(blockResponseDto.getCount());
                block.setCreatedAt(LocalDateTime.now());

                blockDao.saveBlock(block);
                logger.info("Saved Block: {}", block);
            }
        } catch (WebClientResponseException | IOException e) {
            logger.error("Error occurred while fetching results: ", e);
            e.printStackTrace();
        }

        return blockResponseDto;
    }
}
