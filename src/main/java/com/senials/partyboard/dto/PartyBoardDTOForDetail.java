package com.senials.partyboard.dto;

import com.senials.partyboardimage.dto.PartyBoardImageDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartyBoardDTOForDetail {

    private int partyBoardNumber; // auto

    private int userNumber;

    private int hobbyNumber;

    private String partyBoardName;

    private String partyBoardDetail;

    private LocalDate partyBoardOpenDate; // now()

    private int partyBoardStatus; // 0-모집중, 1-모집완료

    private int partyBoardViewCnt;

    private int partyBoardLikeCnt;

    private List<PartyBoardImageDTO> images;

    // 신고 수 안보냄
    // private int partyBoardReportCnt;

    /* AllArgsConstructor */
    public PartyBoardDTOForDetail(int partyBoardNumber, int userNumber, int hobbyNumber, String partyBoardName, String partyBoardDetail, LocalDate partyBoardOpenDate, int partyBoardStatus, int partyBoardViewCnt, int partyBoardLikeCnt) {
        this.partyBoardNumber = partyBoardNumber;
        this.userNumber = userNumber;
        this.hobbyNumber = hobbyNumber;
        this.partyBoardName = partyBoardName;
        this.partyBoardDetail = partyBoardDetail;
        this.partyBoardOpenDate = partyBoardOpenDate;
        this.partyBoardStatus = partyBoardStatus;
        this.partyBoardViewCnt = partyBoardViewCnt;
        this.partyBoardLikeCnt = partyBoardLikeCnt;
    }
}
