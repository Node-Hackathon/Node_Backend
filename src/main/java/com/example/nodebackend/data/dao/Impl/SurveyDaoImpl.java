package com.example.nodebackend.data.dao.Impl;

import com.example.nodebackend.data.dao.SurveyDao;
import com.example.nodebackend.data.entity.Survey;
import com.example.nodebackend.data.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SurveyDaoImpl implements SurveyDao {

    private final SurveyRepository surveyRepository;

    @Override
    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }
}
