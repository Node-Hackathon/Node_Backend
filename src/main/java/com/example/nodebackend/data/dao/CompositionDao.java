package com.example.nodebackend.data.dao;


import com.example.nodebackend.data.dto.CompositionDto.CompositionResultResponseDto;
import com.example.nodebackend.data.entity.Composition;
import java.util.List;

public interface CompositionDao {
    void save(Composition composition);
    List<CompositionResultResponseDto> getCompositionResultList(Long id);
}
