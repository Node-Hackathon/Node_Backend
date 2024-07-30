package com.example.nodebackend.service.Impl;

import com.example.nodebackend.data.entity.CardGameResult;
import com.example.nodebackend.data.repository.CardGameResultRepository;
import com.example.nodebackend.service.CardGameResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardGameResultServiceImpl implements CardGameResultService {

    private final CardGameResultRepository cardGameResultRepository;

    @Autowired
    public CardGameResultServiceImpl(CardGameResultRepository cardGameResultRepository) {
        this.cardGameResultRepository = cardGameResultRepository;
    }

    @Override
    public CardGameResult createCardGameResult(CardGameResult cardGameResult) {
        return cardGameResultRepository.save(cardGameResult);
    }
}
