package com.example.nodebackend.mapper;

import com.example.nodebackend.data.dto.MyPageDto.MyPageGuardianDto;
import com.example.nodebackend.data.entity.User;
//유저 정보 조회 mapper
public class MyPageGuardianMapper {
    public static MyPageGuardianDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return MyPageGuardianDto.builder()
                .guardian_name(user.getGuardian_name())
                .guardian_phone_num(user.getGuardian_phone_num())
                .guardian_address(user.getGuardian_address())
                .build();
    }

}
