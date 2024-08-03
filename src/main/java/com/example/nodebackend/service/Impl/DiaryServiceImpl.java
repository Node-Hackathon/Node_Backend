package com.example.nodebackend.service.Impl;

import com.example.nodebackend.controller.DiaryController;
import com.example.nodebackend.data.dao.DiaryDao;
import com.example.nodebackend.data.dto.DiaryDto.DiaryDto;
import com.example.nodebackend.data.dto.DiaryDto.DiaryResponseDto;
import com.example.nodebackend.data.entity.Diary;
import com.example.nodebackend.data.entity.DiaryAnswer;
import com.example.nodebackend.data.entity.DiaryQuestion;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.DiaryRepository;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.exception.DiaryAlreadyExistsException;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final Logger logger = LoggerFactory.getLogger(DiaryController.class);
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final DiaryRepository diaryRepository;
    private final DiaryDao diaryDao;

    @Override
    public List<Diary> getDiaryByUserId(Long userId) {
        return diaryRepository.findByUserId(userId);
    }

    @Override
    public List<Diary> getDiaryByUserIdAndDate(Long userId, LocalDate date) {
        return diaryRepository.findByUserIdAndDate(userId, date);
    }


    @Override
    public DiaryResponseDto createDiary(DiaryDto diaryDto, HttpServletRequest request) {
        String phoneNum = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
        User user = userRepository.findByPhoneNum(phoneNum);

        // 오늘 일기가 이미 존재하는지 확인
        if (hasDiaryForToday(request)) {
            throw new DiaryAlreadyExistsException("오늘 일기를 이미 작성했습니다.");
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

        Diary savedDiary = diaryDao.createDiary(diary);

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


        return responseDto;
    }

    @Override
    public List<DiaryResponseDto> getDiaryResults(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        LocalDate today = LocalDate.now();
        List<Diary> diaryList = getDiaryByUserIdAndDate(user.getId(), today);
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
        return responseDtoList;
    }
    @Override
    public boolean hasDiaryForToday(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        LocalDate today = LocalDate.now();
        List<Diary> diaryList = getDiaryByUserIdAndDate(user.getId(), today);

        return !diaryList.isEmpty();
    }


    private DiaryAnswer createDiaryAnswer(Diary diary, Long questionId, String answer) {
        DiaryAnswer diaryAnswer = new DiaryAnswer();
        diaryAnswer.setAnswer(answer);
        diaryAnswer.setQuestion(new DiaryQuestion(questionId, null));
        diaryAnswer.setDiary(diary);
        return diaryAnswer;
    }
}
