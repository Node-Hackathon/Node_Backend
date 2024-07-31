package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.GameResultDto.CardGameResultDto;
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
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/update")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<CardGameResultResponseDto> createCardGameResult(@RequestBody CardGameResultDto cardGameResultDto, HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        CardGameResult cardGameResult = CardGameResult.builder()
                .stage(cardGameResultDto.getStage())
                .date(LocalDate.now())
                .user(user)
                .build();
        CardGameResult savedCardGameResult = cardGameResultService.createCardGameResult(cardGameResult);

        logger.info("카드 맞추기 게임 결과 id: {}, date: {}, score: {}, userId: {}",
                savedCardGameResult.getId(),
                savedCardGameResult.getDate(),
                savedCardGameResult.getStage(),
                savedCardGameResult.getUser().getId());

        CardGameResultResponseDto responseDto = new CardGameResultResponseDto();
        responseDto.setId(savedCardGameResult.getId());
        responseDto.setStage(savedCardGameResult.getStage());
        responseDto.setDate(savedCardGameResult.getDate());
        responseDto.setUserId(savedCardGameResult.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/compare")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<String> compareLastResults(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        Long userId = user.getId();
        List<CardGameResult> userResults = cardGameResultService.getCardGameResultsByUserId(userId);

        List<CardGameResult> recentResults = userResults.stream()
                .sorted(Comparator.comparing(CardGameResult::getId).reversed())
                .limit(2)
                .collect(Collectors.toList());

        if (recentResults.size() < 1) {
            return ResponseEntity.ok("");
        }

        Integer a = recentResults.get(0).getStage();
        Integer b = recentResults.get(1).getStage();

        String message;
        if (a > b) {
            message = "저번 게임보다 높은 점수를 기록했어요";
        } else if (a < b) {
            message = "저번 게임보다 낮은 점수를 기록했어요";
        } else {
            message = "저번 게임과 동일한 점수를 기록했어요";
        }

        logger.info("비교 결과: {}", message);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/inquiry-all")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<CardGameResultResponseDto>> getCardGameResultsByUser(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token); //
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Long userId = user.getId();
        List<CardGameResult> userDetails = cardGameResultService.getCardGameResultsByUserId(userId);

        List<CardGameResultResponseDto> responseDtos = userDetails.stream()
                .map(result -> {
                    CardGameResultResponseDto dto = new CardGameResultResponseDto();
                    dto.setId(result.getId());
                    dto.setStage(result.getStage());
                    dto.setDate(result.getDate());
                    dto.setUserId(result.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/inquiry-five")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<CardGameResultResponseDto>> getRecentCardGameResults(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Long userId = user.getId();
        List<CardGameResult> userResults = cardGameResultService.getCardGameResultsByUserId(userId);

        if (userResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<CardGameResult> recentResults = userResults.stream()
                .sorted(Comparator.comparing(CardGameResult::getId).reversed())
                .limit(5)
                .collect(Collectors.toList());

        List<CardGameResultResponseDto> responseDtos = recentResults.stream()
                .map(result -> {
                    CardGameResultResponseDto dto = new CardGameResultResponseDto();
                    dto.setId(result.getId());
                    dto.setStage(result.getStage());
                    dto.setDate(result.getDate());
                    dto.setUserId(result.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }
}