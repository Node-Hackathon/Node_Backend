package com.example.nodebackend.service;

import com.example.nodebackend.data.entity.CardGameResult;
import com.example.nodebackend.data.entity.NumberGameResult;

import java.util.List;

public interface NumberGameResultService {
    NumberGameResult createNumberGameResult(NumberGameResult numberGameResult);
    List<NumberGameResult> getNumberGameResultsByUserId(Long userId);
}
