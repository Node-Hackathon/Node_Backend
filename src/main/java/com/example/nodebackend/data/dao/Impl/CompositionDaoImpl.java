package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.CompositionDao;
import com.example.nodebackend.data.entity.Composition;
import com.example.nodebackend.data.repository.CompositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompositionDaoImpl implements CompositionDao {

    private final CompositionRepository compositionRepository;

    @Override
    public void save(Composition composition) {
        compositionRepository.save(composition);
    }
}
