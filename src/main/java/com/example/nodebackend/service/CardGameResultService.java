package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.GameResultDto.CardGameResultDto;
import com.example.nodebackend.data.entity.CardGameResult;

public interface CardGameResultService {
    CardGameResult createCardGameResult(CardGameResult cardGameResult);
}