package com.example.nodebackend.data.dto.SurveyDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SurveyResponseDto {
    private Long id;
    private int score;
    private LocalDate date;
    private Long userId;
}
