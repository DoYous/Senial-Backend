package com.senials.security.filter;

import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
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

import java.io.BufferedReader;
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
        String username = null;
        String password = null;

        System.out.println("Received Content-Type: " + request.getContentType()); // 수신 확인

        // Content-Type 확인
        if (request.getContentType() != null && request.getContentType().startsWith("application/json")) {
            // JSON 요청 처리
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // JSON 파싱
            String json = sb.toString();
            System.out.println("Received JSON: " + json); // JSON 요청 로그
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            // 사용자 이름과 비밀번호 추출
            username = jsonObject.get("userName").getAsString();
            password = jsonObject.get("userPwd").getAsString();

            // 추출된 사용자 이름과 비밀번호 로그
            System.out.println("Parsed username: " + username);
            System.out.println("Parsed password: " + password);
        } else {
            // 폼 데이터 처리
            System.out.println("요기가 돌아가네요");
            username = request.getParameter("userName");
            password = request.getParameter("userPwd");
        }

        System.out.println("attemptAuthentication - 입력된 사용자 이름: " + username);
        System.out.println("attemptAuthentication - 입력된 비밀번호: " + password);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }










    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // SecurityContext에 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authResult);

        System.out.println("successfulAuthentication - 인증 성공!");
        System.out.println("successfulAuthentication - 인증된 유저 : " + authResult.getName());
        System.out.println("successfulAuthentication - 인증 권한: " + authResult.getAuthorities());

        try {
            String token = jwtService.generateToken(authResult.getName());
            response.addHeader("Authorization", "Bearer " + token);
            System.out.println("생성된 JWT: " + token); // JWT 로그 출력
        } catch (Exception e) {
            e.printStackTrace(); // 에러 로그 출력
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "JWT 생성 실패");
            return; // 예외 발생 시 더 이상 진행하지 않음
        }

//        chain.doFilter(request, response); // 성공 페이지 처리
        response.getWriter().write("인증이 성공했습니다."); // 성공 메시지를 클라이언트에 반환
        response.setStatus(HttpServletResponse.SC_OK); // HTTP 200 상태 코드
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
