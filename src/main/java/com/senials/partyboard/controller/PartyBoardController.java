package com.senials.partyboard.controller;

import com.senials.common.ResponseMessage;
import com.senials.partyboard.dto.PartyBoardDTOForModify;
import com.senials.partyboard.dto.PartyBoardDTOForWrite;
import com.senials.partyboard.service.PartyBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.charset.StandardCharsets;

@RestController
public class PartyBoardController {

    private final PartyBoardService partyBoardService;

    @Autowired
    public PartyBoardController(
            PartyBoardService partyBoardService
    )
    {
        this.partyBoardService = partyBoardService;
    }


    /* 모임 글 작성 */
    @PostMapping("/partyboards")
    public ResponseEntity<ResponseMessage> registerPartyBoard(
            @ModelAttribute PartyBoardDTOForWrite newPartyBoardDTO
    ) {

        // 유저 번호 임의 지정
        int userNumber = 4;

        // 글 작성 후 자동 생성된 글 번호
        int registeredPartyBoardNumber = partyBoardService.registerPartyBoard(userNumber, newPartyBoardDTO);


        /* 이미지 저장 임시 디렉터리 명 변경 */
        String tempDirStr = "src/main/resources/static/img/party_board/" + newPartyBoardDTO.getTempNumber();
        String newDirStr = "src/main/resources/static/img/party_board/" + registeredPartyBoardNumber;

        File tempDir = new File(tempDirStr);
        File newFileDir = new File(newDirStr);
        tempDir.renameTo(newFileDir);


        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "글 작성 성공", null));
    }

    /* 모임 글 수정 */
    @PutMapping("/partyboards/{partyBoardNumber}")
    public ResponseEntity<ResponseMessage> modifyPartyBoard(
            @PathVariable int partyBoardNumber,
            @ModelAttribute PartyBoardDTOForModify partyBoardDTO
    ) {
        // PathVariable의 partyBoardNumber를 DTO에 삽입
        partyBoardDTO.setPartyBoardNumber(partyBoardNumber);

        // form 태그로 테스트할 때 공백이 리스트에 삽입되는 것 방지
        // partyBoardDTO.getRemovedFileNumbers().removeAll(partyBoardDTO.getRemovedFileNumbers());
        // partyBoardDTO.getAddedFiles().removeAll(partyBoardDTO.getAddedFiles());

        partyBoardService.modifyPartyBoard(partyBoardDTO);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "글 수정 성공", null));
    }


    /* 모임 글 삭제 */
    @DeleteMapping("/partyboards/{partyBoardNumber}")
    public ResponseEntity<ResponseMessage> removePartyBoard(
            @PathVariable int partyBoardNumber
    ) {

        partyBoardService.removePartyBoard(partyBoardNumber);

        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "글 삭제 성공", null));
    }

}
