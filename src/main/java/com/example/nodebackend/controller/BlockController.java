package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.BlockDto.BlockResponseDto;
import com.example.nodebackend.data.entity.Block;
import com.example.nodebackend.service.BlockService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/4d-api")
public class BlockController {

    @Autowired
    private BlockService blockService;
    @ApiImplicitParam(name = "X-AUTH-TOKEN",value="로그인 성공 후 발급 받은 access_token", required = true, dataType ="String",paramType = "header")
    @PostMapping("/block")
    public ResponseEntity<BlockResponseDto> uploadFiles(
            @RequestPart("blockImage")MultipartFile blockImage, HttpServletRequest request) throws IOException {

        BlockResponseDto results = blockService.BlockStacking(blockImage,request);
        return ResponseEntity.status(HttpStatus.OK).body(results);

    }


}
