package com.senials.example.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO {

    private int number;

    private String password;

    private String name;

    private LocalDate birth;

    private String email;

    private int gender;

    private int reportCnt;

    private int status;

    private String nickname;

    private String detail;

    private String profileImg;

    private LocalDate sigupDate;

    private String uuid;

    /* AllArgsConstructor */
    public UserDTO(int number, String password, String name, LocalDate birth, String email, int gender, int reportCnt, int status, String nickname, String detail, String profileImg, LocalDate sigupDate, String uuid) {
        this.number = number;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.email = email;
        this.gender = gender;
        this.reportCnt = reportCnt;
        this.status = status;
        this.nickname = nickname;
        this.detail = detail;
        this.profileImg = profileImg;
        this.sigupDate = sigupDate;
        this.uuid = uuid;
    }

}
