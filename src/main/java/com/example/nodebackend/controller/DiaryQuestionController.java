package com.example.nodebackend.controller;

import com.example.nodebackend.data.entity.DiaryQuestion;
import com.example.nodebackend.service.DiaryQuestionService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/diary-question-api")
public class DiaryQuestionController {

    private final DiaryQuestionService diaryQuestionService;

    @Autowired
    public DiaryQuestionController(DiaryQuestionService diaryQuestionService) {
        this.diaryQuestionService = diaryQuestionService;
    }

    @GetMapping("/all")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<DiaryQuestion>> getAllDiaryQuestion(HttpServletRequest request) {
        List<DiaryQuestion> diaryQuestions = diaryQuestionService.getAllDiaryQuestion(request);
        return ResponseEntity.ok(diaryQuestions);
    }

    @GetMapping("/{id}")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<DiaryQuestion> getDiaryQuestionById(@PathVariable Long id, HttpServletRequest request) {
        DiaryQuestion diaryQuestion = diaryQuestionService.getDiaryQuestionById(id, request);
        return ResponseEntity.ok(diaryQuestion);
    }
}
