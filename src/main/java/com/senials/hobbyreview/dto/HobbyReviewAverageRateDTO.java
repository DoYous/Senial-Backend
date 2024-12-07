package com.senials.hobbyreview.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class HobbyReviewAverageRateDTO {

    private int hobbyNumber; // 취미 고유 번호
    private double hobbyReviewRateAVG; // 취미별 평균 평점
    private int hobbyReviewCount; // 취미별 후기 개수

    public HobbyReviewAverageRateDTO(int hobbyNumber, double hobbyReviewRateAVG, int hobbyReviewCount){
        this.hobbyNumber=hobbyNumber;
        this.hobbyReviewRateAVG=hobbyReviewRateAVG;
        this.hobbyReviewCount=hobbyReviewCount;
    }

}
