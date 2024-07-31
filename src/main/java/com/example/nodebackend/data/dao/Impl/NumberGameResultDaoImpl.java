package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.NumberGameResultDao;
import com.example.nodebackend.data.entity.NumberGameResult;
import com.example.nodebackend.data.repository.NumberGameResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NumberGameResultDaoImpl implements NumberGameResultDao {
    private final NumberGameResultRepository numberGameResultRepository;

    @Override
    public NumberGameResult saveNumberGameResult(NumberGameResult numberGameResult) {
        return numberGameResultRepository.save(numberGameResult);
    }
}
