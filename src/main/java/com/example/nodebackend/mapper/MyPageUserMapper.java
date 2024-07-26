package com.example.nodebackend.mapper;

import com.example.nodebackend.data.dto.MyPageDto.MyPageUserDto;
import com.example.nodebackend.data.entity.User;

public class MyPageUserMapper {

    public static MyPageUserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        String guardian_name = user.getGuardian_name();
        if("보호자이름".equals(guardian_name)) {
            guardian_name = "미기입";
        }

        return MyPageUserDto.builder()
                .profile_image_url(user.getProfile_image_url())
                .name(user.getName())
                .guardian_name(guardian_name)
                .gender(user.getGender())
                .birth(user.getBirth())
                .height(user.getHeight())
                .weight(user.getWeight())
                .address(user.getAddress())
                .phoneNum(user.getPhoneNum())
                .build();
    }

}
