package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.GameResultDto.NumberGameResultDto;
import com.example.nodebackend.data.dto.GameResultDto.NumberGameResultResponseDto;
import com.example.nodebackend.service.NumberGameResultService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/numbergame-api")
public class NumberGameResultController {
    private final NumberGameResultService numberGameResultService;

    @Autowired
    public NumberGameResultController(NumberGameResultService numberGameResultService) {
        this.numberGameResultService = numberGameResultService;
    }

    @PostMapping("/update")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<NumberGameResultResponseDto> createNumberGameResult(@RequestBody NumberGameResultDto numberGameResultDto, HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        NumberGameResultResponseDto responseDto = numberGameResultService.createNumberGameResult(numberGameResultDto, token);

        if (responseDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/compare")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<String> compareNumberGameResult(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        try {
            String result = numberGameResultService.compareNumberGameResults(token);
            return ResponseEntity.ok(result);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("JSON 처리 오류");
        }
    }

    @GetMapping("/inquiry-all")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<NumberGameResultResponseDto>> getAllNumberGameResults(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        List<NumberGameResultResponseDto> responseDtos = numberGameResultService.getAllNumberGameResults(token);

        if (responseDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/inquiry-five")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<List<NumberGameResultResponseDto>> getFiveNumberGameResults(HttpServletRequest request) {
        String token = request.getHeader("X-AUTH-TOKEN");
        List<NumberGameResultResponseDto> responseDtos = numberGameResultService.getFiveNumberGameResults(token);

        if (responseDtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(responseDtos);
    }
}
