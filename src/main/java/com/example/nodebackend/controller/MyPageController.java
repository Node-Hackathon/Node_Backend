package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.MyPageDto.MyPageGuardianDto;
import com.example.nodebackend.data.dto.MyPageDto.MyPageUserDto;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.mapper.MyPageGuardianMapper;
import com.example.nodebackend.mapper.MyPageUserMapper;
import com.example.nodebackend.service.Impl.MyPageUserServiceImpl;
import com.example.nodebackend.service.MyPageUserService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mypage-api")
public class MyPageController {

    @Autowired
    private MyPageUserServiceImpl myPageUserServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @GetMapping("/mypage-inquiry")//유저 정보 조회
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<MyPageUserDto> getUserById(HttpServletRequest request) {
        String username = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));

        User user = userRepository.findByPhoneNum(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Long id = user.getId();
        var userDetails = myPageUserServiceImpl.getUserById(id);

        return userDetails.map(value -> ResponseEntity.ok(MyPageUserMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/mypage-update")//유저 정보 수정
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<MyPageUserDto> updateUser(HttpServletRequest request, @RequestBody MyPageUserDto myPageUserDto) {
        String username = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
        User user = userRepository.findByPhoneNum(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setProfile_image_url(myPageUserDto.getProfile_image_url());
        user.setName(myPageUserDto.getName());
        user.setGender(myPageUserDto.getGender());
        user.setBirth(myPageUserDto.getBirth());
        user.setHeight(myPageUserDto.getHeight());
        user.setWeight(myPageUserDto.getWeight());
        user.setAddress(myPageUserDto.getAddress());
        user.setPhoneNum(myPageUserDto.getPhoneNum());

        userRepository.save(user);
        return ResponseEntity.ok(MyPageUserMapper.toDto(user));
    }

    @GetMapping("/guardianpage-inquiry")//보호자 정보 조회
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<MyPageGuardianDto> getGuardianById(HttpServletRequest request) {
        String username = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));

        User user = userRepository.findByPhoneNum(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Long id = user.getId();
        var userDetails = myPageUserServiceImpl.getUserById(id);

        return userDetails.map(value -> ResponseEntity.ok(MyPageGuardianMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/guardian-update")//보호자 정보 수정
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<MyPageGuardianDto> updateGuardian(HttpServletRequest request, @RequestBody MyPageGuardianDto myPageGuardianDto) {
        String username = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
        User user = userRepository.findByPhoneNum(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setGuardian_name(myPageGuardianDto.getGuardian_name());
        user.setGuardian_phone_num(myPageGuardianDto.getGuardian_phone_num());
        user.setGuardian_address(myPageGuardianDto.getGuardian_address());

        userRepository.save(user);
        return ResponseEntity.ok(MyPageGuardianMapper.toDto(user));
    }
}
