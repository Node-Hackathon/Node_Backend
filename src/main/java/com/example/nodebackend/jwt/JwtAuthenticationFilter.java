package com.example.nodebackend.jwt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = jwtProvider.resolveToken(request);
        logger.info("[doFilterInternal] token 값 추출 완료. token: {}", token);

        if (token != null && jwtProvider.validToken(token)) {
            try {
                Authentication authentication = jwtProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("[doFilterInternal] token 값 유효성 체크 완료");
            } catch (Exception e) {
                logger.error("[doFilterInternal] 인증 과정에서 오류 발생: {}", e.getMessage());
            }
        } else {
            logger.warn("[doFilterInternal] 유효하지 않은 token: {}", token);
        }

        filterChain.doFilter(request, response);
    }




}
