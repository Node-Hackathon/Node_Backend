package com.example.nodebackend.data.dto.DiaryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class DiaryStatusResponseDto {
    private boolean status;
    private String message;
}
