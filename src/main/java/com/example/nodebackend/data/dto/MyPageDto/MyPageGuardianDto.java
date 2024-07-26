package com.example.nodebackend.data.dto.MyPageDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageGuardianDto {
    private String guardian_name;
    private String guardian_phone_num;
    private String guardian_address;
}
