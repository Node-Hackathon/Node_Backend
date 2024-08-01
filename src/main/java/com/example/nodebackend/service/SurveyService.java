package com.example.nodebackend.service;

import com.example.nodebackend.data.entity.Survey;

import java.util.List;

public interface SurveyService {
    Survey createSurvey(Survey survey);
    List<Survey> getSurveysByUserId(Long userId);

}
