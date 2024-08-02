package com.example.nodebackend.data.dto.BlockDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BlockResultReponseDto {

    private Long id;
    private String imageUrl;
    private LocalDate createdAt;
}
