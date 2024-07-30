package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.BlockDto.BlockResponseDto;
import com.example.nodebackend.data.entity.Block;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface BlockService {
    BlockResponseDto BlockStacking(MultipartFile blockImage, HttpServletRequest request)throws IOException;
    Map<String, String> getRandomBlockSentence();
}
