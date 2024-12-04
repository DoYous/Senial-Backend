package com.senials.meet.controller;

import com.senials.meet.MeetDTO.MeetDTO;
import com.senials.meet.service.MeetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users/{userNumber}/meets")
public class MeetController {

    private final MeetService meetService;

    public MeetController(MeetService meetService) {
        this.meetService = meetService;
    }

    @GetMapping
    public ResponseEntity<List<MeetDTO>> getUserMeets(@PathVariable int userNumber) {
        List<MeetDTO> meets = meetService.getMeetsByUserNumber(userNumber);
        return ResponseEntity.ok(meets);
    }
}
