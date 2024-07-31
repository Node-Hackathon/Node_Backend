package com.example.nodebackend.service.Impl;

import com.example.nodebackend.data.entity.NumberGameResult;
import com.example.nodebackend.data.repository.NumberGameResultRepository;
import com.example.nodebackend.service.NumberGameResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NumberGameResultServiceImpl implements NumberGameResultService {
    private final NumberGameResultRepository numberGameResultRepository;

    @Autowired
    public NumberGameResultServiceImpl(NumberGameResultRepository numberGameResultRepository) {
        this.numberGameResultRepository = numberGameResultRepository;
    }

    @Override
    public NumberGameResult createNumberGameResult(NumberGameResult numberGameResult) {
        numberGameResult.setDate(LocalDate.now());
        return numberGameResultRepository.save(numberGameResult);
    }

    @Override
    public List<NumberGameResult> getNumberGameResultsByUserId(Long userId) {
        return numberGameResultRepository.findByUserId(userId);
    }
}
