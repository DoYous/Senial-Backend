package com.senials.report.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReportDTO {

    private int reportNumber;

    private int reporterNumber;

    private int userNumber;

    private int partyBoardNumber;

    private int partyReviewNumber;

    private int hobbyReviewNumber;

    private int reportType;

    private String reportDetail;

    private int reportTargetType;

    private int reportTargetNumber;

    private LocalDateTime reportDate;


    /* AllArgsConstructor */
    @Builder
    public ReportDTO(int reportNumber, int reporterNumber, int userNumber, int partyBoardNumber, int partyReviewNumber, int hobbyReviewNumber, int reportType, String reportDetail, int reportTargetType, int reportTargetNumber, LocalDateTime reportDate) {
        this.reportNumber = reportNumber;
        this.reporterNumber = reporterNumber;
        this.userNumber = userNumber;
        this.partyBoardNumber = partyBoardNumber;
        this.partyReviewNumber = partyReviewNumber;
        this.hobbyReviewNumber = hobbyReviewNumber;
        this.reportType = reportType;
        this.reportDetail = reportDetail;
        this.reportTargetType = reportTargetType;
        this.reportTargetNumber = reportTargetNumber;
        this.reportDate = reportDate;
    }
}
