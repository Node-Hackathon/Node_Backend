package com.example.nodebackend.data.repository;

import com.example.nodebackend.data.entity.CardGameResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardGameResultRepository extends JpaRepository<CardGameResult,Long> {
    List<CardGameResult> findByUserId(Long userId);
}
