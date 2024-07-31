package com.example.nodebackend.service.Impl;

import com.example.nodebackend.data.entity.CardGameResult;
import com.example.nodebackend.data.repository.CardGameResultRepository;
import com.example.nodebackend.service.CardGameResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardGameResultServiceImpl implements CardGameResultService {

    private final CardGameResultRepository cardGameResultRepository;

    @Autowired
    public CardGameResultServiceImpl(CardGameResultRepository cardGameResultRepository) {
        this.cardGameResultRepository = cardGameResultRepository;
    }

    @Override//카드 맞추기 게임 결과값 넣기
    public CardGameResult createCardGameResult(CardGameResult cardGameResult) {
        cardGameResult.setDate(LocalDate.now());
        return cardGameResultRepository.save(cardGameResult);
    }

    @Override//결과값 전체 조회
    public List<CardGameResult> getCardGameResultsByUserId(Long userId) {
        return cardGameResultRepository.findByUserId(userId); // 사용자 ID로 결과 조회
    }
}
