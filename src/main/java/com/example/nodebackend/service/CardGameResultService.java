package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.GameResultDto.CardGameResultDto;
import com.example.nodebackend.data.dto.GameResultDto.CardGameResultResponseDto;
import com.example.nodebackend.data.entity.CardGameResult;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CardGameResultService {
    List<CardGameResult> getCardGameResultsByUserId(Long userId);
    CardGameResultResponseDto createCardGameResult(CardGameResultDto cardGameResultDto, String token);
    String compareLastResults(String token) throws JsonProcessingException;
    List<CardGameResultResponseDto> getCardGameResults(String token);
    List<CardGameResultResponseDto> getFiveCardGameResults(String token);
}