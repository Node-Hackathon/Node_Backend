package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.DiaryDto.DiaryDto;
import com.example.nodebackend.data.dto.DiaryDto.DiaryResponseDto;
import com.example.nodebackend.data.entity.Diary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

public interface DiaryService {
    List<Diary> getDiaryByUserId(Long userId);
    List<Diary> getDiaryByUserIdAndDate(Long userId, LocalDate date);

    DiaryResponseDto createDiary( DiaryDto diaryDto, HttpServletRequest request);

    List<DiaryResponseDto> getDiaryResults(HttpServletRequest request);
    boolean hasDiaryForToday(HttpServletRequest request);
}
