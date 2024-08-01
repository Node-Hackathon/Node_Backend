package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.SurveyDto.SurveyDto;
import com.example.nodebackend.data.dto.SurveyDto.SurveyResponseDto;
import com.example.nodebackend.data.entity.Survey;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.service.SurveyService;
import com.example.nodebackend.jwt.JwtProvider;
import io.swagger.annotations.ApiImplicitParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/survey-api")
public class SurveyController {

    private final Logger logger = LoggerFactory.getLogger(SurveyController.class);
    private final SurveyService surveyService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public SurveyController(SurveyService surveyService, UserRepository userRepository, JwtProvider jwtProvider) {
        this.surveyService = surveyService;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/result")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<SurveyResponseDto> createSurvey(@RequestBody SurveyDto surveyDto, HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Survey survey = Survey.builder()
                .score(surveyDto.getScore())
                .date(LocalDate.now())
                .user(user)
                .build();
        Survey savedSurvey = surveyService.createSurvey(survey);

        logger.info("설문조사 결과 id: {}, date: {}, score: {}, userId: {}", savedSurvey.getId(), savedSurvey.getDate(), savedSurvey.getScore(), savedSurvey.getUser().getId());

        SurveyResponseDto responseDto = new SurveyResponseDto();
        responseDto.setId(savedSurvey.getId());
        responseDto.setScore(savedSurvey.getScore());
        responseDto.setDate(savedSurvey.getDate());
        responseDto.setUserId(savedSurvey.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    @GetMapping("/result-list")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<SurveyResponseDto>> getSurveyResults(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Survey> surveys = surveyService.getSurveysByUserId(user.getId());
        List<SurveyResponseDto> responseDtos = surveys.stream().map(survey -> {
            SurveyResponseDto dto = new SurveyResponseDto();
            dto.setId(survey.getId());
            dto.setScore(survey.getScore());
            dto.setDate(survey.getDate());
            dto.setUserId(survey.getUser().getId());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }
}
