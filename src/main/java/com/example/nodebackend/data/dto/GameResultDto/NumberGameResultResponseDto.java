package com.example.nodebackend.data.dto.GameResultDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NumberGameResultResponseDto {
    private Long id;
    private int stage;
    private LocalDate date;
    private Long userId;
}
