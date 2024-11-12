package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.DiaryDto.DiaryDto;
import com.example.nodebackend.data.dto.DiaryDto.DiaryResponseDto;
import com.example.nodebackend.data.entity.Diary;
import com.example.nodebackend.data.entity.DiaryAnswer;
import com.example.nodebackend.data.entity.DiaryQuestion;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.service.DiaryService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/diary-api")
public class DiaryController {

    private final Logger logger = LoggerFactory.getLogger(DiaryController.class);
    private final DiaryService diaryService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public DiaryController(DiaryService diaryService, UserRepository userRepository, JwtProvider jwtProvider) {
        this.diaryService = diaryService;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/result")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<DiaryResponseDto> createDiary(@RequestBody DiaryDto diaryDto, HttpServletRequest request){
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Diary diary = Diary.builder()
                .date(LocalDate.now())
                .user(user)
                .build();

        List<DiaryAnswer> diaryAnswers = new ArrayList<>();
        diaryAnswers.add(createDiaryAnswer(diary, 1L, diaryDto.getAnswer1()));
        diaryAnswers.add(createDiaryAnswer(diary, 2L, diaryDto.getAnswer2()));
        diaryAnswers.add(createDiaryAnswer(diary, 3L, diaryDto.getAnswer3()));
        diaryAnswers.add(createDiaryAnswer(diary, 4L, diaryDto.getAnswer4()));
        diaryAnswers.add(createDiaryAnswer(diary, 5L, diaryDto.getAnswer5()));

        diary.setDiaryAnswers(diaryAnswers);

        Diary savedDiary = diaryService.createDiary(diary);

        logger.info("일기 작성 결과 id: {}, date: {}, userId: {}", savedDiary.getId(), savedDiary.getDate(), savedDiary.getUser().getId());

        DiaryResponseDto responseDto = new DiaryResponseDto();
        responseDto.setId(savedDiary.getId());
        responseDto.setDate(savedDiary.getDate());
        responseDto.setUserId(savedDiary.getUser().getId());
        responseDto.setAnswer1(savedDiary.getDiaryAnswers().get(0).getAnswer());
        responseDto.setAnswer2(savedDiary.getDiaryAnswers().get(1).getAnswer());
        responseDto.setAnswer3(savedDiary.getDiaryAnswers().get(2).getAnswer());
        responseDto.setAnswer4(savedDiary.getDiaryAnswers().get(3).getAnswer());
        responseDto.setAnswer5(savedDiary.getDiaryAnswers().get(4).getAnswer());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    private DiaryAnswer createDiaryAnswer(Diary diary, Long questionId, String answer) {
        DiaryAnswer diaryAnswer = new DiaryAnswer();
        diaryAnswer.setAnswer(answer);
        diaryAnswer.setQuestion(new DiaryQuestion(questionId, null));
        diaryAnswer.setDiary(diary);
        return diaryAnswer;
    }

    @GetMapping("/result-list")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<DiaryResponseDto>> getDiaryResults(HttpServletRequest request){
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if(user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        LocalDate today = LocalDate.now();
        List<Diary> diaryList = diaryService.getDiaryByUserIdAndDate(user.getId(), today);
        List<DiaryResponseDto> responseDtoList = diaryList.stream().map(diary -> {
            DiaryResponseDto responseDto = new DiaryResponseDto();
            responseDto.setId(diary.getId());
            responseDto.setDate(diary.getDate());
            responseDto.setUserId(diary.getUser().getId());
            responseDto.setAnswer1(diary.getDiaryAnswers().get(0).getAnswer());
            responseDto.setAnswer2(diary.getDiaryAnswers().get(1).getAnswer());
            responseDto.setAnswer3(diary.getDiaryAnswers().get(2).getAnswer());
            responseDto.setAnswer4(diary.getDiaryAnswers().get(3).getAnswer());
            responseDto.setAnswer5(diary.getDiaryAnswers().get(4).getAnswer());
            return responseDto;
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }
}
