package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.CompositionDao;
import com.example.nodebackend.data.dto.CompositionDto.CompositionResultResponseDto;
import com.example.nodebackend.data.entity.Composition;
import com.example.nodebackend.data.repository.CompositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompositionDaoImpl implements CompositionDao {

    private final CompositionRepository compositionRepository;

    @Override
    public void save(Composition composition) {
        compositionRepository.save(composition);
    }

    @Override
    public List<CompositionResultResponseDto> getCompositionResultList(Long id) {
        List<Composition> compositions = compositionRepository.getByUserId(id);
        List<CompositionResultResponseDto> compositionResultResponseDtos = compositions.stream().map(composition -> {
            CompositionResultResponseDto compositionResultResponseDto = new CompositionResultResponseDto();
            compositionResultResponseDto.setId(composition.getId());
            compositionResultResponseDto.setImageUrl(composition.getComposition_imageUrl());
            compositionResultResponseDto.setCreatedAt(composition.getCreatedAT());
            return compositionResultResponseDto;
        }).collect(Collectors.toList());

        return compositionResultResponseDtos;
    }
}
