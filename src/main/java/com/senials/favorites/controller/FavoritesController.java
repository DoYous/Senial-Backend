package com.senials.favorites.controller;

import com.senials.favorites.service.FavoritesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class FavoritesController {
    private final FavoritesService favoritesService;

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    // 사용자 관심사 제목 가져오기
    @GetMapping("/{userNumber}/favorites")
    public ResponseEntity<List<String>> getUserFavoriteTitles(@PathVariable int userNumber) {
        List<String> favoriteTitles = favoritesService.getFavoriteTitlesByUser(userNumber);
        return ResponseEntity.ok(favoriteTitles);
    }
}
