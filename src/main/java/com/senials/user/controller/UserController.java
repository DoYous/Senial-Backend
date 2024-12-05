package com.senials.user.controller;

import com.senials.common.ResponseMessage;
import com.senials.partyboard.dto.PartyBoardDTOForCard;
import com.senials.user.dto.UserCommonDTO;
import com.senials.user.dto.UserDTO;
import com.senials.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;

    }
    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<ResponseMessage> getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        List<UserDTO> users = userService.getAllUsers();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "전체 사용자 조회 성공", responseMap));
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

    // 특정 사용자 수정 put
    @PutMapping("/{userNumber}/modify")
    public ResponseEntity<ResponseMessage> updateUserProfile(
            @PathVariable int userNumber,
            @RequestBody Map<String, String> updatedFields) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        boolean isUpdated = userService.updateUserProfile(
                userNumber,
                updatedFields.get("userNickname"),
                updatedFields.get("userDetail"),
                updatedFields.get("userProfileImg")
        );

        if (!isUpdated) {
            return ResponseEntity.status(404)
                    .headers(headers)
                    .body(new ResponseMessage(404, "사용자를 찾을 수 없습니다.", null));
        }

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "사용자 프로필 수정 성공", null));
    }




    //사용자 별 참여한 모임 출력
    // 사용자별 참여 모임 목록 조회
    @GetMapping("/{userNumber}/parties")
    public ResponseEntity<ResponseMessage> getUserJoinedPartyBoards(
            @PathVariable int userNumber,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "9") int size) {
        // Service 호출
        List<PartyBoardDTOForCard> joinedParties = userService.getJoinedPartyBoardsByUserNumber(userNumber, page, size);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        if (joinedParties.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ResponseMessage(404, "사용자가 참여한 모임이 없습니다.", null));
        }

        // 응답 데이터 생성
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("joinedParties", joinedParties);
        responseMap.put("currentPage", page);
        responseMap.put("pageSize", size);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "사용자가 참여한 모임 조회 성공", responseMap));
    }

    //사용자 별 자신이 만든 모임 조회
    @GetMapping("/{userNumber}/made")
    public ResponseEntity<ResponseMessage> getUserMadeParties(
            @PathVariable int userNumber,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "9") int size) {

        // Service 호출
        List<PartyBoardDTOForCard> madeParties = userService.getMadePartyBoardsByUserNumber(userNumber, page, size);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        if (madeParties.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(new ResponseMessage(404, "사용자가 만든 모임이 없습니다.", null));
        }

        // 응답 데이터 생성
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("madeParties", madeParties);
        responseMap.put("currentPage", page);
        responseMap.put("pageSize", size);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "사용자가 만든 모임 조회 성공", responseMap));
    }


    /*모임 개수 api*/

    /*사용자 별 참여한 모임 개수*/
    @GetMapping("/{userNumber}/parties/count")
    public ResponseEntity<ResponseMessage> countUserJoinedParties(@PathVariable int userNumber) {
        long count = userService.countPartiesPartyBoardsByUserNumber(userNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("partiesPartyCount", count);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "사용자가 참여한 모임 개수 조회 성공", responseMap));
    }
  
    /*사용자 별 만든 모임 개수*/
    @GetMapping("/{userNumber}/made/count")
    public ResponseEntity<ResponseMessage> countUserMadeParties(@PathVariable int userNumber) {
        long count = userService.countMadePartyBoardsByUserNumber(userNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("madePartyCount", count);

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "사용자가 만든 모임 개수 조회 성공", responseMap));

    }

}
