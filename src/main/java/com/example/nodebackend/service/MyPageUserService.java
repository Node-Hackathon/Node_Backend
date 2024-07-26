package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.MyPageDto.MyPageGuardianDto;
import com.example.nodebackend.data.dto.MyPageDto.MyPageUserDto;
import com.example.nodebackend.data.entity.User;
import java.util.Optional;

public interface MyPageUserService {
    Optional<User> getUserById(Long id);
    MyPageUserDto updateUser(Long id, MyPageUserDto userDto);
    MyPageGuardianDto updateGuardian(Long id, MyPageGuardianDto guardianDto);
}
