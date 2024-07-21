package com.example.nodebackend.service.Impl;

import com.example.nodebackend.S3.S3Uploader;
import com.example.nodebackend.data.dao.SignDao;
import com.example.nodebackend.data.dto.CommonResponse;
import com.example.nodebackend.data.dto.SignDto.*;
import com.example.nodebackend.data.entity.User;
import com.example.nodebackend.data.repository.UserRepository;
import com.example.nodebackend.jwt.JwtProvider;
import com.example.nodebackend.service.SignService;
import com.example.nodebackend.service.SmsService;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class SignServiceImpl implements SignService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final SmsService smsService;
    private final SignDao signDao;
    private final S3Uploader s3Uploader;

    private Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);

    public SignServiceImpl(UserRepository userRepository,
                           JwtProvider jwtProvider,
                           PasswordEncoder passwordEncoder,
                           SmsService smsService,
                           SignDao signDao,
                           S3Uploader s3Uploader) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.smsService = smsService;
        this.signDao = signDao;
        this.s3Uploader = s3Uploader;
    }




    @Override
    public SignUpResultDto SignUpVerification(String certification, HttpServletRequest request) {
        String partialPhoneNum = (String) request.getSession().getAttribute("partial_phone_num");
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        // SMS 인증번호 검증
        SmsCertificationDto smsCertificationDto = new SmsCertificationDto();
        smsCertificationDto.setPhone_num(partialPhoneNum);
        smsCertificationDto.setCertification_num(certification);
        if (smsService.verifySms(certification, request)) {
            // 인증 성공 시 RDBMS에 전화번호 저장
            User user = User.builder()
                    .phoneNum(partialPhoneNum)
                    .certification_num(true)
                    .build();
            request.getSession().setAttribute("partialUser", user);

            signUpResultDto.setDetailMessage("인증 성공");
            setSuccess(signUpResultDto);
        } else {
            signUpResultDto.setDetailMessage("인증 실패");
            setFail(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignUpResultDto SignUpFirst(SignUpUserInfoDto signUpUserInfoDto, HttpServletRequest request) {
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        User partialUser = (User) request.getSession().getAttribute("partialUser");

        if (partialUser != null) {
            // 기존 partialUser 업데이트
            partialUser.setName(signUpUserInfoDto.getName());
            partialUser.setAddress(signUpUserInfoDto.getAddress());
            partialUser.setBirth(signUpUserInfoDto.getBirth());
            partialUser.setGender(signUpUserInfoDto.getGender());
            partialUser.setHeight(signUpUserInfoDto.getHeight());

            logger.info("[user 정보 입력] : {} ", partialUser);

            request.getSession().setAttribute("partialUser", partialUser);
            signUpResultDto.setDetailMessage("다음 단계로 넘어가세요.");
            setSuccess(signUpResultDto);
        } else {
           setFail(signUpResultDto);
        }

        return signUpResultDto;
    }

    @Override
    public SignUpResultDto SignUpSecond(String userId, String password, String passwordCheck, MultipartFile profile_image, HttpServletRequest request) throws IOException {
        User partialUser = (User) request.getSession().getAttribute("partialUser");

        SignUpResultDto signUpResultDto = new SignUpResultDto();

        if (userRepository.existsByUserId(userId)) {
            signUpResultDto.setDetailMessage("아이디가 중복되었습니다.");
            setFail(signUpResultDto);
            return signUpResultDto;
        }

        if (!password.equals(passwordCheck)) {
            signUpResultDto.setDetailMessage("비밀번호가 일치하지 않습니다.");
            setFail(signUpResultDto);
            return signUpResultDto;
        }

        String imageUrl = s3Uploader.uploadImage(profile_image, "image/profile/");

        if (partialUser != null) {
            // 기존 partialUser 업데이트
            partialUser.setUserId(userId);
            partialUser.setPassword(passwordEncoder.encode(password));
            partialUser.setPasswordCheck(passwordEncoder.encode(passwordCheck));
            partialUser.setProfile_image_url(imageUrl);

            request.getSession().setAttribute("partialUser", partialUser);
            signUpResultDto.setDetailMessage("다음 단계로 넘어가세요.");
            setSuccess(signUpResultDto);
        } else {
            setFail(signUpResultDto);
        }

        return signUpResultDto;
    }

    @Override
    public SignUpResultDto SignUpGuardian(SignUpGuardianInfoDto signUpGuardianInfoDto, HttpServletRequest request) {
        User partialUser = (User) request.getSession().getAttribute("partialUser");
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        if (partialUser != null) {
            // 기존 partialUser 업데이트
            partialUser.setGuardian_name(signUpGuardianInfoDto.getGuardian_name());
            partialUser.setGuardian_phone_num(signUpGuardianInfoDto.getGuardian_phone_num());
            partialUser.setGuardian_address(signUpGuardianInfoDto.getGuardian_address());
            partialUser.setCreatedAt(LocalDateTime.now());

            signDao.SignUp(partialUser);
            signUpResultDto.setDetailMessage("회원 가입이 완료되었습니다.");
            setSuccess(signUpResultDto);
        } else {
            setFail(signUpResultDto);
        }

        return signUpResultDto;
    }

    @Override
    public SignInResultDto SignIn(String userId, String password) {
        User user = userRepository.getByUserId(userId);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        logger.info("[getSignInResult] 패스워드 일치");

        logger.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = new SignInResultDto().builder()
                .token(jwtProvider.createToken(String.valueOf(user.getPhoneNum()), user.getRoles()))
                .build();
        logger.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccess(signInResultDto);
        signInResultDto.setDetailMessage("로그인 성공");
        return signInResultDto;
    }

    private void setSuccess(SignUpResultDto signUpResultDto) {
        signUpResultDto.setSuccess(true);
        signUpResultDto.setCode(CommonResponse.SUCCESS.getCode());
        signUpResultDto.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFail(SignUpResultDto signUpResultDto) {
        signUpResultDto.setSuccess(false);
        signUpResultDto.setCode(CommonResponse.Fail.getCode());
        signUpResultDto.setMsg(CommonResponse.Fail.getMsg());
    }
}
