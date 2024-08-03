package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.MyPageDto.MyPageGuardianDto;
import com.example.nodebackend.data.dto.MyPageDto.MyPageUserDto;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.mapper.MyPageGuardianMapper;
import com.example.nodebackend.mapper.MyPageUserMapper;
import com.example.nodebackend.service.MyPageUserService;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.ApiImplicitParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/mypage-api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageUserService myPageUserService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    @GetMapping("/mypage-inquiry") // 유저 정보 조회
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<MyPageUserDto> getUserById(HttpServletRequest request) {
        String username = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));

        User user = userRepository.findByPhoneNum(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Long id = user.getId();
        var userDetails = myPageUserService.getUserById(id);

        return userDetails.map(value -> ResponseEntity.ok(MyPageUserMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/mypage-update") // 유저 정보 수정
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<MyPageUserDto> updateUser(HttpServletRequest request,
                                                    @RequestBody MyPageUserDto myPageUserDto) {
        String username = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
        User user = userRepository.findByPhoneNum(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        MyPageUserDto updatedUser = myPageUserService.updateUser(user.getId(), myPageUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/mypage-update-profile-image") // 프로필 이미지 수정
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<MyPageUserDto> updateProfileImage(HttpServletRequest request,
                                                            @RequestPart("profileImage") MultipartFile profileImage) {
        String username = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
        User user = userRepository.findByPhoneNum(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            MyPageUserDto updatedUser = myPageUserService.updateProfileImage(user.getId(), profileImage);
            return ResponseEntity.ok(updatedUser);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/guardianpage-inquiry") // 보호자 정보 조회
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<MyPageGuardianDto> getGuardianById(HttpServletRequest request) {
        String username = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));

        User user = userRepository.findByPhoneNum(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Long id = user.getId();
        var userDetails = myPageUserService.getUserById(id);

        return userDetails.map(value -> ResponseEntity.ok(MyPageGuardianMapper.toDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/guardian-update")
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 발급 받은 access_token", required = true, dataType = "String", paramType = "header")
    public ResponseEntity<MyPageGuardianDto> updateGuardian(HttpServletRequest request, @RequestBody MyPageGuardianDto myPageGuardianDto) {
        String username = jwtProvider.getUsername(request.getHeader("X-AUTH-TOKEN"));
        User user = userRepository.findByPhoneNum(username);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        MyPageGuardianDto updatedGuardian = myPageUserService.updateGuardian(user.getId(), myPageGuardianDto);
        return ResponseEntity.ok(updatedGuardian);
    }
}
