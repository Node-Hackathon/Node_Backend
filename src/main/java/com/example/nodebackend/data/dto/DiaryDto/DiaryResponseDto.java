package com.example.nodebackend.data.dto.DiaryDto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DiaryResponseDto {
    private Long id;
    private LocalDate date;
    private Long userId;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String answer5;
}
