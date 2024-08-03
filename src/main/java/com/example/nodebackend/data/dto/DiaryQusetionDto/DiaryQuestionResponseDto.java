package com.example.nodebackend.data.dto.DiaryQusetionDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DiaryQuestionResponseDto {
    private Long id;
    private String question;
}
