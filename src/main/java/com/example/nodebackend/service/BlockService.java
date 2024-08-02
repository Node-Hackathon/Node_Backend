package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.BlockDto.BlockResponseDto;
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
