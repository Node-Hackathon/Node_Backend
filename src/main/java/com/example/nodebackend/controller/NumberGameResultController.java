package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.GameResultDto.NumberGameResultDto;
import com.example.nodebackend.data.dto.GameResultDto.NumberGameResultResponseDto;
import com.example.nodebackend.data.entity.NumberGameResult;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.service.NumberGameResultService;
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
@RequestMapping("/numbergame-api")
public class NumberGameResultController {
    private final Logger logger = LoggerFactory.getLogger(NumberGameResultController.class);
    private final NumberGameResultService numberGameResultService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public NumberGameResultController(NumberGameResultService numberGameResultService, UserRepository userRepository, JwtProvider jwtProvider) {
        this.numberGameResultService = numberGameResultService;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/update")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<NumberGameResultResponseDto> createNumberGameResult(@RequestBody NumberGameResultDto numberGameResultDto, HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        NumberGameResult numberGameResult = NumberGameResult.builder()
                .stage(numberGameResultDto.getStage())
                .date(LocalDate.now())
                .user(user)
                .build();
        NumberGameResult savedNumberGameResult = numberGameResultService.createNumberGameResult(numberGameResult);

        logger.info("숫자 맞추기 게임 결과 id: {}, date: {}, score: {}, userId: {}",
                savedNumberGameResult.getId(),
                savedNumberGameResult.getDate(),
                savedNumberGameResult.getStage(),
                savedNumberGameResult.getUser().getId());

        NumberGameResultResponseDto responseDto = new NumberGameResultResponseDto();
        responseDto.setId(savedNumberGameResult.getId());
        responseDto.setStage(savedNumberGameResult.getStage());
        responseDto.setDate(savedNumberGameResult.getDate());
        responseDto.setUserId(savedNumberGameResult.getUser().getId());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/compare")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<String> compareNumberGameResult(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("사용자를 찾을 수 없습니다.");
        }

        Long userId = user.getId();
        List<NumberGameResult> userResults = numberGameResultService.getNumberGameResultsByUserId(userId);

        List<NumberGameResult> recentResults = userResults.stream()
                .sorted(Comparator.comparing(NumberGameResult::getId).reversed())
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
    public ResponseEntity<List<NumberGameResultResponseDto>> getNumberGameResultsByUser(HttpServletRequest request){
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token); //
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Long userId = user.getId();
        List<NumberGameResult> userDetails = numberGameResultService.getNumberGameResultsByUserId(userId);

        List<NumberGameResultResponseDto> responseDtos = userDetails.stream()
                .map(result -> {
                    NumberGameResultResponseDto dto = new NumberGameResultResponseDto();
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
    public ResponseEntity<List<NumberGameResultResponseDto>> getRecentNumberGameResults(HttpServletRequest request){
        String token = request.getHeader("X-AUTH-TOKEN");
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Long userId = user.getId();
        List<NumberGameResult> userResults = numberGameResultService.getNumberGameResultsByUserId(userId);

        if (userResults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<NumberGameResult> recentResults = userResults.stream()
                .sorted(Comparator.comparing(NumberGameResult::getId).reversed())
                .limit(5)
                .collect(Collectors.toList());

        List<NumberGameResultResponseDto> responseDtos = recentResults.stream()
                .map(result -> {
                    NumberGameResultResponseDto dto = new NumberGameResultResponseDto();
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
