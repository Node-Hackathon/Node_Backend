package com.example.nodebackend.service;

import com.example.nodebackend.data.entity.CardGameResult;

import java.util.List;

public interface CardGameResultService {
    CardGameResult createCardGameResult(CardGameResult cardGameResult);
    List<CardGameResult> getCardGameResultsByUserId(Long userId);
}