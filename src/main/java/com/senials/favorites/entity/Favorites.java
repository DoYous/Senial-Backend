package com.senials.favorites.entity;

import com.senials.hobbyboard.entity.Hobby;
import com.senials.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "FAVORITES")
public class Favorites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_number")
    private int favoriteNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_number", referencedColumnName = "user_number", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "hobby_number", referencedColumnName = "hobby_number", nullable = false)
    private Hobby hobby;

    /* AllArgsConstructor */
    @Builder
    public Favorites(int favoriteNumber, User user, Hobby hobby) {
        this.favoriteNumber = favoriteNumber;
        this.user = user;
        this.hobby = hobby;
    }

}
