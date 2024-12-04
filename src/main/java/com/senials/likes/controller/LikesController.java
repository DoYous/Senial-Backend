package com.senials.likes.controller;

import com.senials.likes.service.LikesService;
import com.senials.partyboard.dto.PartyBoardDTOForCard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class LikesController {
    private final LikesService likesService;

    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    // 사용자가 좋아한 모임 목록
    @GetMapping("/{userNumber}/likes")
    public ResponseEntity<List<PartyBoardDTOForCard>> getLikedPartyBoards(@PathVariable int userNumber) {
        List<PartyBoardDTOForCard> likedBoards = likesService.getLikedPartyBoardsByUserNumber(userNumber);
        return ResponseEntity.ok(likedBoards);
    }

    //사용자가 좋아한 상태별 모임 목록
    @GetMapping("/{userNumber}/likes/{partyBoardStatus}")
    public ResponseEntity<List<PartyBoardDTOForCard>> getLikedPartyBoardsStatus(@PathVariable int userNumber) {
        List<PartyBoardDTOForCard> likedBoards = likesService.getLikedPartyBoardsByUserNumber(userNumber);
        return ResponseEntity.ok(likedBoards);
    }
}
