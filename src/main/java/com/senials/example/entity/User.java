package com.senials.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;

    @Column(name = "user_pwd")
    private String password;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_birth")
    private LocalDate birth;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_gender")
    private int gender;

    @Column(name = "user_report_cnt")
    private int reportCnt;

    @Column(name = "user_status")
    private int status;

    @Column(name = "user_nickname")
    private String nickname;

    @Column(name = "user_detail")
    private String detail;

    @Column(name = "user_profile_img")
    private String profileImg;

    @Column(name = "user_signup_date")
    private LocalDate sigupDate;

    @Column(name = "user_uuid")
    private String uuid;


    /* AllArgsConstructor */
    public User(int number, String password, String name, LocalDate birth, String email, int gender, int reportCnt, int status, String nickname, String detail, String profileImg, LocalDate sigupDate, String uuid) {
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
