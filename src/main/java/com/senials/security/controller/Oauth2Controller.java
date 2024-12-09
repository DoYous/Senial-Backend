package com.senials.security.controller;

import com.senials.security.domain.kakao.auth.PrincipalDetails;
import com.senials.security.repository.SecurityUserRepository;
import com.senials.security.service.PrincipalOauth2UserService;
import com.senials.user.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Controller
public class Oauth2Controller {
    private final SecurityUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    @Autowired
    private AuthenticationManager authenticationManager;


    public Oauth2Controller(SecurityUserRepository userRepository, PasswordEncoder passwordEncoder, PrincipalOauth2UserService principalOauth2UserService, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.httpSession = httpSession;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/join")
    public String joinForm() {
        return "join";
    }

    @GetMapping("/success")
    public String successPage(Model model) {
        System.out.println("성공페이지 불러오는중");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 인증 정보 가져오기
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal(); // PrincipalDetails 가져오기
            User user = principalDetails.getUser(); // User 객체 가져오기
            model.addAttribute("user", user); // 모델에 사용자 정보 추가
            httpSession.getId();
            model.addAttribute("sessionID", httpSession.getId());
            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("sessionActive", true); //세션 활성화됨
                model.addAttribute("username", authentication.getName());
                System.out.println("세션돌아가요");
            } else {
                model.addAttribute("sessionActive", false); //세션 비활성화됨
                System.out.println("세션안돌아가요");
            }
            System.out.println("여기야11");
        } else {
            System.out.println("여기야22");
            return "redirect:/login"; // 인증 정보가 없을 경우 로그인 페이지로 리디렉션
        }
        return "success"; // 성공 페이지를 반환
    }

    @GetMapping("/fail")
    @ResponseBody
    public String loginFail() {
        return "너 로그인 실패했어";
    }


    @GetMapping("/oauth/loginInfo")
    @ResponseBody
    public String oauthLoginInfo(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2UserPrincipal) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "사용자가 인증되지 않았습니다.";
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return attributes.toString();
    }

    @PostMapping("/join")
    public String join(@RequestBody User user) {
        // 가입 요청 로깅
        System.out.println("가입 요청: " + user);

        // 비밀번호가 null인지 확인
        if (user.getUserPwd() == null) {
            throw new IllegalArgumentException("비밀번호는 null일 수 없습니다.");
        }

        // 비밀번호를 변수에 저장
        String rawPassword = user.getUserPwd();

        // 비밀번호 암호화
        String encodePassword = passwordEncoder.encode(rawPassword);
        user.initializePwd(encodePassword); // 사용자 객체에 암호화된 비밀번호 저장
        user.initializeSignupDate(); // 가입 날짜 초기화
        user.initializeUuid(); // UUID 초기화

        System.out.println("생성된 UUID: " + user.getUserUuid());

        // 사용자 정보를 저장
        userRepository.save(user);

        // 자동 로그인 처리
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), rawPassword)); // 입력된 비밀번호 사용

            // SecurityContext에 인증 정보 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            System.out.println("자동 로그인 실패: " + e.getMessage());
        }

        return "redirect:/success"; // 가입 후 성공 페이지로 리디렉션
    }






    @GetMapping("/loginInfo")
    @ResponseBody
    public String loginInfo(Authentication authentication, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "사용자가 인증되지 않았습니다.";
        }

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        return "OAuth2 로그인 : " + principal;
    }

    /*@PostMapping("/checkPassword")
    @ResponseBody
    public String checkPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password"); // 사용자가 입력한 비밀번호

        User user = userRepository.findByUsername(username);
        if (user != null) {
            String storedHash = user.getUserPwd(); // 데이터베이스에서 가져온 비밀번호 해시
            boolean isMatch = passwordEncoder.matches(password, storedHash); // 비교

            // 콘솔에 비밀번호 매칭 결과 출력
            System.out.println("사용자 이름: " + username);
            System.out.println("입력된 비밀번호: " + password);
            System.out.println("저장된 비밀번호 해시: " + storedHash);
            System.out.println("비밀번호 매칭 결과: " + isMatch); // true 또는 false

            return isMatch ? "비밀번호가 일치합니다." : "비밀번호가 일치하지 않습니다.";
        } else {
            return "사용자를 찾을 수 없습니다.";
        }
    }*/
}
