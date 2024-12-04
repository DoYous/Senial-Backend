package com.senials.partyboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartyBoardDTOForModify {

    private int partyBoardNumber;

    private int hobbyNumber;

    private String partyBoardName;

    private String partyBoardDetail;

    private int partyBoardStatus;

    // 삭제 이미지 번호 리스트
    private List<Integer> removedFileNumbers;

    // 추가 이미지 이름 리스트
    private List<String> addedFiles;

    /* AllArgsConstructor */
    public PartyBoardDTOForModify(int partyBoardNumber, int hobbyNumber, String partyBoardName, String partyBoardDetail, int partyBoardStatus, List<Integer> removedFileNumbers, List<String> addedFiles) {
        this.partyBoardNumber = partyBoardNumber;
        this.hobbyNumber = hobbyNumber;
        this.partyBoardName = partyBoardName;
        this.partyBoardDetail = partyBoardDetail;
        this.partyBoardStatus = partyBoardStatus;
        this.removedFileNumbers = removedFileNumbers;
        this.addedFiles = addedFiles;
    }
}
