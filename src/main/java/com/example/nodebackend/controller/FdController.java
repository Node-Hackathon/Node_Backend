package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.BlockDto.BlockResponseDto;
import com.example.nodebackend.data.dto.CompositionDto.CompositionResponseDto;
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
    @PostMapping("/composition")
    public ResponseEntity<CompositionResponseDto> CompositionPlay(
            @RequestPart("composition_image") MultipartFile composition_image, HttpServletRequest request) throws IOException {

        CompositionResponseDto compositionResponseDto = composistionService.CompositionPlay(composition_image, request);
        return ResponseEntity.status(HttpStatus.OK).body(compositionResponseDto);

    }

    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    @PostMapping("/composition-sentences")
    public ResponseEntity<String> getRandomSentence() {

        String sentences = composistionService.getRandomSentence();
        return ResponseEntity.status(HttpStatus.OK).body(sentences);

    }
}