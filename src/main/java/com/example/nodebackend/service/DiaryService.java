package com.example.nodebackend.service;

import com.example.nodebackend.data.entity.Diary;
import java.time.LocalDate;
import java.util.List;

public interface DiaryService {
    List<Diary> getDiaryByUserId(Long userId);
    List<Diary> getDiaryByUserIdAndDate(Long userId, LocalDate date);
    Diary createDiary(Diary diary);
}
