package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.CompositionDto.CompositionResponseDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ComposistionService {

    CompositionResponseDto CompositionPlay(MultipartFile composition_image, HttpServletRequest request)throws IOException;


}
