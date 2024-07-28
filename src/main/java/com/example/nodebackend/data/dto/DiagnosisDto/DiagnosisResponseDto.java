package com.example.nodebackend.data.dto.DiagnosisDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisResponseDto {
    private Long id;
    private String diagnosisDetail;
}
