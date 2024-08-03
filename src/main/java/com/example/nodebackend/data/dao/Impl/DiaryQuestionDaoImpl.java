package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.DiaryQuestionDao;
import com.example.nodebackend.data.entity.DiaryQuestion;
import com.example.nodebackend.data.repository.DiaryQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiaryQuestionDaoImpl implements DiaryQuestionDao {
    private final DiaryQuestionRepository diaryQuestionRepository;

    @Autowired
    public DiaryQuestionDaoImpl(DiaryQuestionRepository diaryQuestionRepository) {
        this.diaryQuestionRepository = diaryQuestionRepository;
    }

    @Override
    public List<DiaryQuestion> getAllDiaryQuestions() {
        return diaryQuestionRepository.findAll();
    }
}
