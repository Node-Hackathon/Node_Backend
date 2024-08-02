package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.BlockDto.BlockResponseDto;
import com.example.nodebackend.data.dto.BlockDto.BlockResultReponseDto;
import com.example.nodebackend.data.dto.CompositionDto.CompositionResponseDto;
import com.example.nodebackend.data.dto.CompositionDto.CompositionResultResponseDto;
import com.example.nodebackend.service.BlockService;
import com.example.nodebackend.service.ComposistionService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/4d-api")
@RequiredArgsConstructor
public class FdController {

    private final BlockService blockService;
    private final ComposistionService composistionService;

    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    @PostMapping("/block")
    public ResponseEntity<BlockResponseDto> BlockPlay(
            @RequestPart("blockImage") MultipartFile blockImage, HttpServletRequest request) throws IOException {

        BlockResponseDto results = blockService.BlockStacking(blockImage, request);
        return ResponseEntity.status(HttpStatus.OK).body(results);

    }


    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    @PostMapping("/block-sentences")
    public ResponseEntity<Map<String, String>> getRandomBlcokSentence() {
        Map<String, String> block_sentence = blockService.getRandomBlockSentence();
        return ResponseEntity.status(HttpStatus.OK).body(block_sentence);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    @GetMapping ("/block-result")
    public ResponseEntity<List<BlockResultReponseDto>> BlockResult(HttpServletRequest request) {
        List<BlockResultReponseDto> blockResultReponseDtos = blockService.getBlockResultImageList(request);
        return ResponseEntity.status(HttpStatus.OK).body(blockResultReponseDtos);
    }




    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    @PostMapping("/composition")
    public ResponseEntity<CompositionResponseDto> CompositionPlay(
            @RequestPart("composition_image") MultipartFile composition_image, HttpServletRequest request) throws IOException {

        CompositionResponseDto compositionResponseDto = composistionService.CompositionPlay(composition_image, request);
        return ResponseEntity.status(HttpStatus.OK).body(compositionResponseDto);

    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    @GetMapping ("/composition-result")
    public ResponseEntity<List<CompositionResultResponseDto>> CompositionResult(HttpServletRequest request) {
        List<CompositionResultResponseDto> compositionResultResponseDtos = composistionService.getCompositionResultList(request);
        return ResponseEntity.status(HttpStatus.OK).body(compositionResultResponseDtos);
    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    @PostMapping("/composition-sentences")
    public ResponseEntity<Map<String, String>> getRandomCompositonSentence() {
        Map<String, String> composition_sentence = composistionService.getRandomCompositionSentence();
        return ResponseEntity.status(HttpStatus.OK).body(composition_sentence);
    }
}