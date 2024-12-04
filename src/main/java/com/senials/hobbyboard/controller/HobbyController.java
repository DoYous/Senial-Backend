package com.senials.hobbyboard.controller;

import com.senials.common.ResponseMessage;
import com.senials.config.HttpHeadersFactory;
import com.senials.hobbyboard.dto.HobbyDTO;
import com.senials.hobbyboard.service.HobbyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class HobbyController {

    private HobbyService hobbyService;
    private final HttpHeadersFactory httpHeadersFactory;

    public HobbyController(HobbyService hobbyService, HttpHeadersFactory httpHeadersFactory){

        this.hobbyService=hobbyService;
        this.httpHeadersFactory = httpHeadersFactory;
    }

    //취미 전체 조회
    @GetMapping("/hobby-board")
    public ResponseEntity<ResponseMessage> findHobbyAll(){

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

        List<HobbyDTO> hobbyDTOList = hobbyService.findAll();

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobby", hobbyDTOList);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

    //취미 상세 조회
    @GetMapping("/hobby-detail/{hobbyNumber}")
    public ResponseEntity<ResponseMessage> findHobbyDetail(@PathVariable("hobbyNumber")int hobbyNumber){

        HttpHeaders headers = httpHeadersFactory.createJsonHeaders();

        HobbyDTO hobbyDTO = hobbyService.findById(hobbyNumber);

        Map<String, Object> responseMap = new HashMap<String, Object>();
        responseMap.put("hobby", hobbyDTO);

        return ResponseEntity.ok().headers(headers).body(new ResponseMessage(200, "조회 성공", responseMap));
    }

}
