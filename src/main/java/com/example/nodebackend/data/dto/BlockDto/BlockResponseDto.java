package com.example.nodebackend.data.dto.BlockDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BlockResponseDto {
    private int count;
    private List<BlockDto> results;
    private String imageUrl;
}