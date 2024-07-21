package com.example.nodebackend.controller;

import com.example.nodebackend.data.dto.SignDto.SignInResultDto;
import com.example.nodebackend.data.dto.SignDto.SignUpGuardianInfoDto;
import com.example.nodebackend.data.dto.SignDto.SignUpUserInfoDto;
import com.example.nodebackend.data.dto.SignDto.SignUpResultDto;
import com.example.nodebackend.service.SignService;
import com.example.nodebackend.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/sign-api")
public class SignController {

    private final Logger logger = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;
    private final SmsService smsService;

    @Autowired
    public SignController(SignService signService, SmsService smsService) {
        this.signService = signService;
        this.smsService = smsService;
    }

    @PostMapping("/send-sms")
    public ResponseEntity<Map<String, String>> sendSMS(String phone_num, HttpServletRequest request) {
        try {
            ResponseEntity<Map<String, String>> response = smsService.sendSMS(phone_num, request);
            logger.info("[문자 인증 진행중] phoneNumber: {}, randomNum: {}", phone_num, response.getBody().get("certification_num"));
            return response;
        } catch (Exception e) {
            logger.error("[문자 인증 실패] phoneNumber: {}, error: {}", phone_num, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "문자 전송 실패"));
        }
    }

    @PostMapping("/sms-verification")
    public ResponseEntity<SignUpResultDto> SignUpVerification(@RequestParam String certification_number, HttpServletRequest request) {
        SignUpResultDto signUpResultDto = signService.SignUpVerification(certification_number, request);
        return ResponseEntity.status(signUpResultDto.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(signUpResultDto);
    }

    @PostMapping(value ="/sign-up-first")
    public ResponseEntity<SignUpResultDto> SignUpFirst(@RequestBody SignUpUserInfoDto signUpUserInfoDto, HttpServletRequest request) throws IOException {
        SignUpResultDto signUpResultDto = signService.SignUpFirst(signUpUserInfoDto ,request);
        return ResponseEntity.status(signUpResultDto.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(signUpResultDto);
    }

    @PostMapping(value ="/sign-up-second")
    public ResponseEntity<SignUpResultDto> SignUpSecond(@RequestPart("userId") String userId,@RequestPart("password")String password,
                                                        @RequestPart("passwordCheck")String passwordCheck,
                                                        @RequestPart("file")MultipartFile profile_image,
                                                        HttpServletRequest request) throws IOException {
        SignUpResultDto signUpResultDto = signService.SignUpSecond(userId,password,passwordCheck,profile_image ,request);
        return ResponseEntity.status(signUpResultDto.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(signUpResultDto);
    }

    @PostMapping(value ="/sign-up-guardian")
    public ResponseEntity<SignUpResultDto> SignUpGuardian(@RequestBody SignUpGuardianInfoDto signUpGuardianInfoDto, HttpServletRequest request) {
        SignUpResultDto signUpResultDto = signService.SignUpGuardian(signUpGuardianInfoDto ,request);
        return ResponseEntity.status(signUpResultDto.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(signUpResultDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResultDto> SignIn(@RequestParam String userId, String password) {
        logger.info("[sign-in] 로그인을 시도하고 있습니다. id : {}, password : *****", userId);
        SignInResultDto signInResultDto = signService.SignIn(userId, password);
        if (signInResultDto.getCode() == 0) {
            logger.info("[sign-in] 정상적으로 로그인이 되었습니다. id: {}, token : {}", userId, signInResultDto.getToken());
        }
        return ResponseEntity.status(signInResultDto.getCode() == 0 ? HttpStatus.OK : HttpStatus.UNAUTHORIZED).body(signInResultDto);
    }
}
