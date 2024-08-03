package com.example.nodebackend.service.Impl;

import com.example.nodebackend.data.dto.GameResultDto.CardGameResultDto;
import com.example.nodebackend.data.dto.GameResultDto.CardGameResultResponseDto;
import com.example.nodebackend.data.entity.CardGameResult;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.CardGameResultRepository;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.service.CardGameResultService;
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
public class CardGameResultServiceImpl implements CardGameResultService {

    private final CardGameResultRepository cardGameResultRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(CardGameResultServiceImpl.class);

    @Autowired
    public CardGameResultServiceImpl(JwtProvider jwtProvider, UserRepository userRepository,
                                     CardGameResultRepository cardGameResultRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
        this.cardGameResultRepository = cardGameResultRepository;
    }

    @Override
    public List<CardGameResult> getCardGameResultsByUserId(Long userId) {
        return cardGameResultRepository.findByUserId(userId);
    }

    @Override
    public CardGameResultResponseDto createCardGameResult(CardGameResultDto cardGameResultDto, String token) {
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return null;
        }

        CardGameResult cardGameResult = CardGameResult.builder()
                .stage(cardGameResultDto.getStage())
                .date(LocalDate.now())
                .user(user)
                .build();

        CardGameResult savedCardGameResult = cardGameResultRepository.save(cardGameResult);

        logger.info("카드 맞추기 게임 결과 id: {}, date: {}, stage: {}, userId: {}",
                savedCardGameResult.getId(),
                savedCardGameResult.getDate(),
                savedCardGameResult.getStage(),
                savedCardGameResult.getUser().getId());

        CardGameResultResponseDto responseDto = new CardGameResultResponseDto();
        responseDto.setId(savedCardGameResult.getId());
        responseDto.setStage(savedCardGameResult.getStage());
        responseDto.setDate(savedCardGameResult.getDate());
        responseDto.setUserId(savedCardGameResult.getUser().getId());

        return responseDto;
    }

    @Override
    public String compareLastResults(String token) throws JsonProcessingException {
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> response = new HashMap<>();

        if (user == null) {
            response.put("message", "사용자를 찾을 수 없습니다.");
            return objectMapper.writeValueAsString(response);
        }

        Long userId = user.getId();
        List<CardGameResult> userResults = getCardGameResultsByUserId(userId);

        List<CardGameResult> recentResults = userResults.stream()
                .sorted(Comparator.comparing(CardGameResult::getId).reversed())
                .limit(2)
                .collect(Collectors.toList());

        if (recentResults.size() < 2) {
            response.put("message", "비교할 이전 결과가 충분하지 않습니다.");
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
    public List<CardGameResultResponseDto> getCardGameResults(String token) {
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return Collections.emptyList();
        }

        Long userId = user.getId();
        List<CardGameResult> userDetails = getCardGameResultsByUserId(userId);

        return userDetails.stream()
                .map(result -> {
                    CardGameResultResponseDto dto = new CardGameResultResponseDto();
                    dto.setId(result.getId());
                    dto.setStage(result.getStage());
                    dto.setDate(result.getDate());
                    dto.setUserId(result.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CardGameResultResponseDto> getFiveCardGameResults(String token) {
        String phoneNum = jwtProvider.getUsername(token);
        User user = userRepository.findByPhoneNum(phoneNum);

        if (user == null) {
            return Collections.emptyList();
        }

        Long userId = user.getId();
        List<CardGameResult> userResults = getCardGameResultsByUserId(userId);

        if (userResults.isEmpty()) {
            return Collections.emptyList();
        }

        List<CardGameResult> recentResults = userResults.stream()
                .sorted(Comparator.comparing(CardGameResult::getId).reversed())
                .limit(5)
                .collect(Collectors.toList());

        return recentResults.stream()
                .map(result -> {
                    CardGameResultResponseDto dto = new CardGameResultResponseDto();
                    dto.setId(result.getId());
                    dto.setStage(result.getStage());
                    dto.setDate(result.getDate());
                    dto.setUserId(result.getUser().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
