package com.example.nodebackend.data.dto.CompositionDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CompositionResultResponseDto {

    private Long id;
    private String imageUrl;
    private LocalDate createdAt;

}
