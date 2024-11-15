package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.DiaryDto.DiaryDto;
import com.example.nodebackend.data.dto.DiaryDto.DiaryResponseDto;
import com.example.nodebackend.data.dto.DiaryDto.DiaryStatusResponseDto;
import com.example.nodebackend.data.entity.Diary;
import com.example.nodebackend.data.entity.DiaryAnswer;
import com.example.nodebackend.data.entity.DiaryQuestion;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.exception.DiaryAlreadyExistsException;
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
    public ResponseEntity<DiaryResponseDto> createDiary(@RequestBody DiaryDto diaryDto, HttpServletRequest request) {
        try {
            DiaryResponseDto responseDto = diaryService.createDiary(diaryDto, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (DiaryAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/result-list")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<DiaryResponseDto>> getDiaryResults(HttpServletRequest request){
        List<DiaryResponseDto> responseDtoList = diaryService.getDiaryResults(request);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);
    }
    @GetMapping("/has-written-today")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<DiaryStatusResponseDto> hasWrittenDiaryToday(HttpServletRequest request){
        boolean hasWrittenToday = diaryService.hasDiaryForToday(request);
        String message = hasWrittenToday ? "오늘 일기 작성이 완료 되었습니다." : "오늘 일기를 작성할 수 있습니다.";
        DiaryStatusResponseDto responseDto = new DiaryStatusResponseDto(hasWrittenToday, message);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
