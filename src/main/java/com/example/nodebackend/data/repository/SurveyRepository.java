package com.example.nodebackend.data.repository;

import com.example.nodebackend.data.entity.Block;
import com.example.nodebackend.data.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    Optional<Survey> findByIdAndUserId(Long id, Long userId);
    List<Survey> findByUserId(Long userId);

}
