package com.example.nodebackend.data.dao;

import java.util.List;

public interface CompositionDao {
    void save(Composition composition);
    List<CompositionResultResponseDto> getCompositionResultList(Long id);
}
