package com.example.nodebackend.data.repository;

import com.example.nodebackend.data.entity.DiaryQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryQuestionRepository extends JpaRepository<DiaryQuestion, Long> {
}
