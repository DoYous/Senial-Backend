package com.senials.favorites.controller;

import com.senials.favorites.dto.FavoriteSelectDTO;
import com.senials.favorites.service.FavoritesService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
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
        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        List<String> favoriteTitles = favoritesService.getFavoriteTitlesByUser(userNumber);
        return ResponseEntity.ok().headers(headers).body(favoriteTitles);
    }

    //모든 취미, 카테고리명, 저장 여부 가져오기
    @GetMapping("/{userNumber}/FavoritesAll")
    public ResponseEntity<List<FavoriteSelectDTO>> getAllHobbiesWithCategoryAndFavoriteStatus(@PathVariable int userNumber) {
        // ResponseHeader 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        List<FavoriteSelectDTO> favoriteSelectList = favoritesService.getAllHobbiesWithCategoryAndFavoriteStatus(userNumber);
        return ResponseEntity.ok().headers(headers).body(favoriteSelectList);
    }

}
