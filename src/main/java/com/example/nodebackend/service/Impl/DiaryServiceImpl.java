package com.example.nodebackend.service.impl;

import com.example.nodebackend.data.entity.Diary;
import com.example.nodebackend.data.repository.DiaryRepository;
import com.example.nodebackend.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final DiaryRepository diaryRepository;

    @Override
    public List<Diary> getDiaryByUserId(Long userId) {
        return diaryRepository.findByUserId(userId);
    }

    @Override
    public List<Diary> getDiaryByUserIdAndDate(Long userId, LocalDate date) {
        return diaryRepository.findByUserIdAndDate(userId, date);
    }

    @Override
    public Diary createDiary(Diary diary) {
        return diaryRepository.save(diary);
    }
}
