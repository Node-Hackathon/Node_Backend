package com.example.nodebackend.service.Impl;

import com.example.nodebackend.data.entity.Survey;
import com.example.nodebackend.data.repository.SurveyRepository;
import com.example.nodebackend.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;

    @Autowired
    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Survey createSurvey(Survey survey) {
        survey.setDate(LocalDate.now());
        return surveyRepository.save(survey);
    }
    @Override
    public List<Survey> getSurveysByUserId(Long userId) {
        return surveyRepository.findByUserId(userId);
    }
}
