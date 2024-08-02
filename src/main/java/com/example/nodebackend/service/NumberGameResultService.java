package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.GameResultDto.NumberGameResultDto;
import com.example.nodebackend.data.dto.GameResultDto.NumberGameResultResponseDto;
import com.example.nodebackend.data.entity.NumberGameResult;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface NumberGameResultService {
    NumberGameResultResponseDto createNumberGameResult(NumberGameResultDto numberGameResultDto, String token);
    List<NumberGameResult> getNumberGameResultsByUserId(Long userId);
    String compareNumberGameResults(String token) throws JsonProcessingException;
    List<NumberGameResultResponseDto> getAllNumberGameResults(String token);
    List<NumberGameResultResponseDto> getFiveNumberGameResults(String token);
}
