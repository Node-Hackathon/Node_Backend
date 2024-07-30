package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.GameResultDto.CardGameResultResponseDto;
import com.example.nodebackend.data.entity.CardGameResult;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.service.CardGameResultService;
import io.swagger.annotations.ApiImplicitParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cardgame-api")
public class CardGameResultController {
    private final Logger logger = LoggerFactory.getLogger(CardGameResultController.class);
    private final CardGameResultService cardGameResultService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public CardGameResultController(CardGameResultService cardGameResultService, UserRepository userRepository, JwtProvider jwtProvider) {
        this.cardGameResultService = cardGameResultService;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/result")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<CardGameResultResponseDto> createCardGameResult(@RequestBody CardGameResult cardGameResult, HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        cardGameResult.setUser(user);

        CardGameResult savedCardGameResult = cardGameResultService.createCardGameResult(cardGameResult);

        logger.info("설문조사 결과 id: {}, date: {}, score: {}, userId: {}",
                savedCardGameResult.getId(),
                savedCardGameResult.getDate(),
                savedCardGameResult.getStage(),
                savedCardGameResult.getUser().getId());
        
        CardGameResultResponseDto responseDto = new CardGameResultResponseDto();
        responseDto.setId(savedCardGameResult.getId());
        responseDto.setScore(savedCardGameResult.getStage());
        responseDto.setDate(savedCardGameResult.getDate());
        responseDto.setUserId(savedCardGameResult.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
