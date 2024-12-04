package com.senials.favorites.repository;

import com.senials.favorites.entity.Favorites;
import com.senials.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Integer> {

    List<Favorites> findAllByUser(User user);

}
