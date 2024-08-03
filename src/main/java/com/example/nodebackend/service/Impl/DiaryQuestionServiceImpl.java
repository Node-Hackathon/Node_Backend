package com.example.nodebackend.service.Impl;

import com.example.nodebackend.data.entity.DiaryQuestion;
import com.example.nodebackend.data.repository.DiaryQuestionRepository;
import com.example.nodebackend.service.DiaryQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class DiaryQuestionServiceImpl implements DiaryQuestionService {
    private final DiaryQuestionRepository diaryQuestionRepository;

    @Autowired
    public DiaryQuestionServiceImpl(DiaryQuestionRepository diaryQuestionRepository) {
        this.diaryQuestionRepository = diaryQuestionRepository;
    }

    @Override
    public List<DiaryQuestion> getAllDiaryQuestion(HttpServletRequest request) {
        return diaryQuestionRepository.findAll();
    }

    @Override
    public DiaryQuestion getDiaryQuestionById(Long id, HttpServletRequest request) {
        Optional<DiaryQuestion> diaryQuestion = diaryQuestionRepository.findById(id);
        return diaryQuestion.orElse(null);
    }
}
