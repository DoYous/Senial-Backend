package com.senials.user.controller;

import com.senials.common.ResponseMessage;
import com.senials.user.dto.UserCommonDTO;
import com.senials.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;

    }

    // 특정 사용자 조회
    @GetMapping("/{userNumber}")
    public ResponseEntity<ResponseMessage> getUserByNumber(@PathVariable int userNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        // userNumber로 데이터 조회
        UserCommonDTO user = userService.getUserByNumber(userNumber);

        if (user == null) {
            return ResponseEntity.status(404)
                    .headers(headers)
                    .body(new ResponseMessage(404, "사용자를 찾을 수 없습니다.", null));
        }

        // 응답 생성 (userNumber는 제외됨)
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", user);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "사용자 조회 성공", responseMap));
    }

    //특정 사용자 탈퇴
    @DeleteMapping("/{userNumber}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable int userNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        boolean isDeleted = userService.deleteUser(userNumber);

        if (!isDeleted) {
            return ResponseEntity.status(404)
                    .headers(headers)
                    .body(new ResponseMessage(404, "회원 탈퇴 실패: 사용자를 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "회원 탈퇴 성공", null));
    }
}
