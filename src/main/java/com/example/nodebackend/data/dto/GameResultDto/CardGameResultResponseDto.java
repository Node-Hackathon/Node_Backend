package com.example.nodebackend.data.dto.GameResultDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardGameResultResponseDto {
    private Long id;
    private int score;
    private String date;
    private Long userId;
}
