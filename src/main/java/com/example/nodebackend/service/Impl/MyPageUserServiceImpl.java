package com.example.nodebackend.service.Impl;

import com.example.nodebackend.data.dto.MyPageDto.MyPageGuardianDto;
import com.example.nodebackend.data.dto.MyPageDto.MyPageUserDto;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.mapper.MyPageGuardianMapper;
import com.example.nodebackend.mapper.MyPageUserMapper;
import com.example.nodebackend.service.MyPageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyPageUserServiceImpl implements MyPageUserService{

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public MyPageUserDto updateUser(Long id, MyPageUserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setProfile_image_url(userDto.getProfile_image_url());
            user.setName(userDto.getName());
            user.setGender(userDto.getGender());
            user.setBirth(userDto.getBirth());
            user.setHeight(userDto.getHeight());
            user.setWeight(userDto.getWeight());
            user.setAddress(userDto.getAddress());
            user.setPhoneNum(userDto.getPhoneNum());

            userRepository.save(user);
            return MyPageUserMapper.toDto(user);
        }
        return null;
    }

    @Override
    public MyPageGuardianDto updateGuardian(Long id, MyPageGuardianDto guardianDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setGuardian_name(guardianDto.getGuardian_name());
            user.setGuardian_phone_num(guardianDto.getGuardian_phone_num());
            user.setGuardian_address(guardianDto.getGuardian_address());

            userRepository.save(user);
            return MyPageGuardianMapper.toDto(user);
        }
        return null;
    }

}
