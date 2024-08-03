package com.example.nodebackend.service;

import com.example.nodebackend.data.entity.Diagnosis;
import com.example.nodebackend.data.entity.DiaryQuestion;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DiaryQuestionService {
    List<DiaryQuestion> getAllDiaryQuestion(HttpServletRequest request);
    DiaryQuestion getDiaryQuestionById(Long id, HttpServletRequest request);
}
