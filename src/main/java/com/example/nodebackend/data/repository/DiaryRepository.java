package com.example.nodebackend.data.repository;

import com.example.nodebackend.data.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByUserId(Long userId);
    List<Diary> findByUserIdAndDate(Long userId, LocalDate date);

    List<Diary> findAllByUserId(Long userId);
}
