package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.BlockDto.BlockResponseDto;
import com.example.nodebackend.data.dto.BlockDto.BlockResultReponseDto;
<<<<<<< HEAD
import com.example.nodebackend.data.entity.Block;
import org.springframework.http.ResponseEntity;
=======
>>>>>>> 3d7371f314980cda1a0b778e858be6b572aef021
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BlockService {
    BlockResponseDto BlockStacking(MultipartFile blockImage, HttpServletRequest request)throws IOException;
    Map<String, String> getRandomBlockSentence();
    List<BlockResultReponseDto> getBlockResultImageList(HttpServletRequest request);
}
