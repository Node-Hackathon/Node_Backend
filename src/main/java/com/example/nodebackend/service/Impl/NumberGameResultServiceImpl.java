package com.example.nodebackend.service.Impl;

import com.example.nodebackend.data.dto.GameResultDto.NumberGameResultDto;
import com.example.nodebackend.data.dto.GameResultDto.NumberGameResultResponseDto;
import com.example.nodebackend.data.entity.NumberGameResult;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.NumberGameResultRepository;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.service.NumberGameResultService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NumberGameResultServiceImpl implements NumberGameResultService {

    private final NumberGameResultRepository numberGameResultRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(NumberGameResultServiceImpl.class);

    @Autowired
    public NumberGameResultServiceImpl(NumberGameResultRepository numberGameResultRepository, JwtProvider jwtProvider, UserRepository userRepository) {
        this.numberGameResultRepository = numberGameResultRepository;
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    @Override
    public NumberGameResultResponseDto createNumberGameResult(NumberGameResultDto numberGameResultDto, String token) {
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return null;
        }

        NumberGameResult numberGameResult = NumberGameResult.builder()
                .stage(numberGameResultDto.getStage())
                .date(LocalDate.now())
                .user(user)
                .build();

        NumberGameResult savedNumberGameResult = numberGameResultRepository.save(numberGameResult);

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

        return responseDto;
    }

    @Override
    public List<NumberGameResult> getNumberGameResultsByUserId(Long userId) {
        return numberGameResultRepository.findByUserId(userId);
    }

    @Override
    public String compareNumberGameResults(String token) throws JsonProcessingException {
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> response = new HashMap<>();

        if (user == null) {
            response.put("message", "사용자를 찾을 수 없습니다.");
            return objectMapper.writeValueAsString(response);
        }

        Long userId = user.getId();
        List<NumberGameResult> userResults = getNumberGameResultsByUserId(userId);

        List<NumberGameResult> recentResults = userResults.stream()
                .sorted(Comparator.comparing(NumberGameResult::getId).reversed())
                .limit(2)
                .collect(Collectors.toList());

        if (recentResults.size() < 1) {
            response.put("message", "");
            return objectMapper.writeValueAsString(response);
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
        response.put("message", message);
        return objectMapper.writeValueAsString(response);
    }

    @Override
    public List<NumberGameResultResponseDto> getAllNumberGameResults(String token) {
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return Collections.emptyList();
        }

        Long userId = user.getId();
        List<NumberGameResult> userDetails = getNumberGameResultsByUserId(userId);

        return userDetails.stream()
                .map(result -> {
                    NumberGameResultResponseDto dto = new NumberGameResultResponseDto();
                    dto.setId(result.getId());
                    dto.setStage(result.getStage());
                    dto.setDate(result.getDate());
                    dto.setUserId(result.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<NumberGameResultResponseDto> getFiveNumberGameResults(String token) {
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return Collections.emptyList();
        }

        Long userId = user.getId();
        List<NumberGameResult> userResults = getNumberGameResultsByUserId(userId);

        if (userResults.isEmpty()) {
            return Collections.emptyList();
        }

        List<NumberGameResult> recentResults = userResults.stream()
                .sorted(Comparator.comparing(NumberGameResult::getId).reversed())
                .limit(5)
                .collect(Collectors.toList());

        return recentResults.stream()
                .map(result -> {
                    NumberGameResultResponseDto dto = new NumberGameResultResponseDto();
                    dto.setId(result.getId());
                    dto.setStage(result.getStage());
                    dto.setDate(result.getDate());
                    dto.setUserId(result.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
