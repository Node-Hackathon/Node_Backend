package com.example.nodebackend.service.Impl;

import com.example.nodebackend.S3.S3Uploader;
import com.example.nodebackend.data.dao.CompositionDao;
import com.example.nodebackend.data.dto.BlockDto.BlockResultReponseDto;
import com.example.nodebackend.data.dto.CompositionDto.CompositionResponseDto;
import com.example.nodebackend.data.dto.CompositionDto.CompositionResultResponseDto;
import com.example.nodebackend.data.entity.Composition;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.CompositionRepository;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.service.ComposistionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;

import java.util.*;

@Service
@Slf4j
public class CompositionServiceImpl implements ComposistionService {


    private final S3Uploader s3Uploader;

    private final JwtProvider jwtProvider;

    private final CompositionRepository compositionRepository;

    private final UserRepository userRepository;

    private final CompositionDao compositionDao;

    private final WebClient webClient;

    private final Logger logger = LoggerFactory.getLogger(CompositionServiceImpl.class);
    private final Random random = new Random();
    public CompositionServiceImpl(S3Uploader s3Uploader, JwtProvider jwtProvider,
                                  CompositionRepository compositionRepository, UserRepository userRepository,
                                  CompositionDao compositionDao, WebClient.Builder webClientBuilder) {
        this.s3Uploader = s3Uploader;
        this.jwtProvider = jwtProvider;
        this.compositionRepository = compositionRepository;
        this.userRepository = userRepository;
        this.compositionDao = compositionDao;
        this.webClient = webClientBuilder.baseUrl("http://43.203.118.158:8001").build();
    }

    public static final List<String> compositionSentences = new ArrayList<>();

    static {
        compositionSentences.add("4D 블럭을 활용하여 자신만의 캐릭터를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 오리를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 첨성대를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 '봄' 글자를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 돛단배를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 '화분을 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 태극기를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 하트를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 다양한 패턴을 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 달팽이를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 발바닥을 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 헬리콥터를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 얼굴을 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 강아지를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 새를 만들어 보세요!");
        compositionSentences.add("4D 블럭을 활용하여 삼지창을 만들어 보세요!");
    }

    @Override
    public Map<String, String> getRandomCompositionSentence(){
        String sentence = compositionSentences.get(random.nextInt(compositionSentences.size()));
        Map<String, String> compositionSentencesResponse = new HashMap<>();
        compositionSentencesResponse.put("sentence", sentence);
        return compositionSentencesResponse;
    }

    @Override
    @Transactional
    public CompositionResponseDto CompositionPlay(MultipartFile composition_image, HttpServletRequest request) throws IOException {
        CompositionResponseDto compositionResponseDto = null;

        try{
            String imageUrl = s3Uploader.uploadImage(composition_image,"image");
            logger.info("imageUrl {} : ",imageUrl);

            Mono<CompositionResponseDto> response = webClient.post()
                    .uri("/composition")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData("file",composition_image.getResource()))
                    .retrieve()
                    .bodyToMono(CompositionResponseDto.class);

            compositionResponseDto = response.block();
            logger.info("Received Response: {} ", compositionResponseDto);

            if(composition_image != null){
                String info = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
                User user = userRepository.findByPhoneNum(info);

                Composition composition = new Composition();
                composition.setComposition_imageUrl(compositionResponseDto.getImage_url());
                composition.setUser(user);
                composition.setCreatedAT(LocalDate.now());

                compositionDao.save(composition);
                logger.info("Saved Composition {} : ",composition);
            }
        }catch (WebClientResponseException  e){
            logger.error("Error occurred while fetching results: ", e);
        }

        return compositionResponseDto;
    }

    @Override
    public List<CompositionResultResponseDto> getCompositionResultList(HttpServletRequest request) {
        String info = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
        User user = userRepository.findByPhoneNum(info);

        Long userId = user.getId();

        List <CompositionResultResponseDto> compositionResultResponseDtoList = compositionDao.getCompositionResultList(userId);


        return compositionResultResponseDtoList;
    }
}
