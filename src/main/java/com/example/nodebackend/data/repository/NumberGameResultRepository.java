package com.example.nodebackend.data.repository;

import com.example.nodebackend.data.entity.NumberGameResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NumberGameResultRepository extends JpaRepository<NumberGameResult,Long> {
    List<NumberGameResult> findByUserId(Long userId);
    List<NumberGameResult> findByUserIdAndStageIn(Long userId, List<Integer> stages);
}
