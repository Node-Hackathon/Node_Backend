package com.example.nodebackend.service.Impl;

import com.example.nodebackend.S3.S3Uploader;
import com.example.nodebackend.data.dto.MyPageDto.MyPageGuardianDto;
import com.example.nodebackend.data.dto.MyPageDto.MyPageUserDto;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.mapper.MyPageGuardianMapper;
import com.example.nodebackend.mapper.MyPageUserMapper;
import com.example.nodebackend.service.MyPageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class MyPageUserServiceImpl implements MyPageUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Uploader s3Uploader;

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public MyPageUserDto updateUser(Long id, MyPageUserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userDto.getName() != null) {
                user.setName(userDto.getName());
            }
            if (userDto.getGender() != null) {
                user.setGender(userDto.getGender());
            }
            if (userDto.getBirth() != null) {
                user.setBirth(userDto.getBirth());
            }
            if (userDto.getHeight() != 0) {
                user.setHeight(userDto.getHeight());
            }
            if (userDto.getWeight() != 0) {
                user.setWeight(userDto.getWeight());
            }
            if (userDto.getAddress() != null) {
                user.setAddress(userDto.getAddress());
            }
            if (userDto.getPhoneNum() != null) {
                user.setPhoneNum(userDto.getPhoneNum());
            }

            userRepository.save(user);
            return MyPageUserMapper.toDto(user);
        }
        return null;
    }

    @Override
    public MyPageUserDto updateProfileImage(Long id, MultipartFile profileImage) throws IOException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (profileImage != null && !profileImage.isEmpty()) {
                String imageUrl = s3Uploader.uploadImage(profileImage, "image/profile/");
                user.setProfile_image_url(imageUrl);
            }

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
            if(guardianDto.getGuardian_name() != null){
                user.setGuardian_name(guardianDto.getGuardian_name());
            }
            if(guardianDto.getGuardian_phone_num() != null){
                user.setGuardian_phone_num(guardianDto.getGuardian_phone_num());
            }
            if(guardianDto.getGuardian_address() != null){
                user.setGuardian_address(guardianDto.getGuardian_address());
            }

            userRepository.save(user);
            return MyPageGuardianMapper.toDto(user);
        }
        return null;
    }
}