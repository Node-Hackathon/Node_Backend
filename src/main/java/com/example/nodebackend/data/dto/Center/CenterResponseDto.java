package com.example.nodebackend.data.dto.Center;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CenterResponseDto {
    private Long id;
    private String center_name;
    private String center_type;
    private String center_address;
    private double latitude;
    private double longitude;
    private String center_phone_num;
}
