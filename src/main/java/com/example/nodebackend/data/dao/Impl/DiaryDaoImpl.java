package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.DiaryDao;
import com.example.nodebackend.data.entity.Diary;
import com.example.nodebackend.data.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryDaoImpl implements DiaryDao {

    private final DiaryRepository diaryRepository;

    @Override
    public Diary createDiary(Diary diary) {
       return diaryRepository.save(diary);
    }
}
