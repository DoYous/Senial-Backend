package com.senials.favorites.dto;

public class FavoriteSelectDTO{
    private String hobbyName;
    private String categoryName;
    private boolean isFavorite;

    public FavoriteSelectDTO(String hobbyName, String categoryName, boolean isFavorite) {
        this.hobbyName = hobbyName;
        this.categoryName = categoryName;
        this.isFavorite = isFavorite;
    }

    // Getters and Setters
    public String getHobbyName() {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
