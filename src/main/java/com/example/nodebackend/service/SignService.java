package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.SignDto.SignInResultDto;
import com.example.nodebackend.data.dto.SignDto.SignUpGuardianInfoDto;
import com.example.nodebackend.data.dto.SignDto.SignUpUserInfoDto;
import com.example.nodebackend.data.dto.SignDto.SignUpResultDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface SignService {
    SignUpResultDto SignUpVerification(String certification_number, HttpServletRequest request);
    SignUpResultDto SignUpFirst(SignUpUserInfoDto signUpUserInfoDto, HttpServletRequest request);
    SignUpResultDto SignUpSecond(String userId,String password,String passwordCheck,MultipartFile profile_image, HttpServletRequest request)throws IOException;

    SignUpResultDto SignUpGuardian(SignUpGuardianInfoDto signUpGuardianInfoDto, HttpServletRequest request);

    SignInResultDto SignIn(String userId, String password);

    void SignSecession(HttpServletRequest request)throws Exception;

}
