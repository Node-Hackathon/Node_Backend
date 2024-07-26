package com.example.nodebackend.data.dto.MyPageDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageUserDto {
    private String profile_image_url;
    private String name;
    private String guardian_name;
    private String gender;
    private String birth;
    private int height;
    private int weight;
    private String address;
    private String phoneNum;
}
