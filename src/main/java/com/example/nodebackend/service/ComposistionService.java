package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.BlockDto.BlockResultReponseDto;
import com.example.nodebackend.data.dto.CompositionDto.CompositionResponseDto;
import com.example.nodebackend.data.dto.CompositionDto.CompositionResultResponseDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ComposistionService {

    CompositionResponseDto CompositionPlay(MultipartFile composition_image, HttpServletRequest request)throws IOException;
    Map<String, String> getRandomCompositionSentence();
    List<CompositionResultResponseDto> getCompositionResultList(HttpServletRequest request);

}
