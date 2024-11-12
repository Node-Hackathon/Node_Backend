package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.GameResultDto.CardGameResultDto;
import com.example.nodebackend.data.dto.GameResultDto.CardGameResultResponseDto;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.service.CardGameResultService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/cardgame-api")
public class CardGameResultController {
    private final CardGameResultService cardGameResultService;

    @Autowired
    public CardGameResultController(CardGameResultService cardGameResultService, UserRepository userRepository, JwtProvider jwtProvider) {
        this.cardGameResultService = cardGameResultService;
    }

    @PostMapping("/update")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<CardGameResultResponseDto> createCardGameResult(
            @RequestBody CardGameResultDto cardGameResultDto, HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        CardGameResultResponseDto responseDto = cardGameResultService.createCardGameResult(cardGameResultDto, token);

        if (responseDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/compare")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<String> compareLastResults(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        try {
            String result = cardGameResultService.compareLastResults(token);
            return ResponseEntity.ok(result);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("JSON 처리 오류");
        }
    }

    @GetMapping("/inquiry-all")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<CardGameResultResponseDto>> getCardGameResults(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        List<CardGameResultResponseDto> responseDtos = cardGameResultService.getCardGameResults(token);

        if (responseDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/inquiry-five")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<CardGameResultResponseDto>> getFiveCardGameResults(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        List<CardGameResultResponseDto> responseDtos = cardGameResultService.getFiveCardGameResults(token);

        if (responseDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(responseDtos);
    }
}