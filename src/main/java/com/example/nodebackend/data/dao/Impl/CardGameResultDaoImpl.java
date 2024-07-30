package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.CardGameResultDao;
import com.example.nodebackend.data.entity.CardGameResult;
import com.example.nodebackend.data.repository.CardGameResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardGameResultDaoImpl implements CardGameResultDao {
    private final CardGameResultRepository cardGameResultRepository;

    @Override
    public CardGameResult saveCardGameResult(CardGameResult cardGameResult) {
        return cardGameResultRepository.save(cardGameResult);
    }
}
