package com.senials.partyboard.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PartyBoardDTOForCard {
    private int partyBoardNumber;
    private String partyBoardName;
    private int partyBoardStatus;
    private long memberCount; // 참여 인원 수
    private double averageRating; // 별점 평균
    private String firstImage; // 첫 번째 이미지 경로
}

