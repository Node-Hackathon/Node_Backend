package com.example.nodebackend.service;

import com.example.nodebackend.data.dto.SignDto.SmsCertificationDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public interface SmsService {
    HashMap<String, String> makeprams(String to, String randomNum);
    String createRandomNumber();
    ResponseEntity<Map<String, String>> sendSMS(String phone_num, HttpServletRequest request) ;
    boolean verifySms(String certification,HttpServletRequest request);

}
