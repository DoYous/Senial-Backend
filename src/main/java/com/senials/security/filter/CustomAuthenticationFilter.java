package com.senials.security.filter;

import com.senials.security.repository.SecurityUserRepository;
import com.senials.security.service.JwtService;
import com.senials.security.service.OAuth2Service;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final OAuth2Service oAuth2Service; // OAuth2Service 추가
    private final SecurityUserRepository userRepository; // UserRepository 추가

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService, OAuth2Service oAuth2Service, SecurityUserRepository securityUserRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.oAuth2Service = oAuth2Service; // OAuth2Service 초기화
        this.userRepository = securityUserRepository; // UserRepository 초기화
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // SecurityContext에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authResult);

        System.out.println("인증 성공!");
        System.out.println("인증된 유저 : " + authResult.getName());
        System.out.println("인증 권한: " + authResult.getAuthorities());

        try {
            String token = jwtService.generateToken(authResult.getName());
            response.addHeader("Authorization", "Bearer " + token);
            System.out.println("생성된 JWT: " + token); // JWT 로그 출력
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JWT 생성 실패");
            return; // 예외 발생 시 더 이상 진행하지 않음
        }

        chain.doFilter(request, response); // 성공 페이지 처리
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
