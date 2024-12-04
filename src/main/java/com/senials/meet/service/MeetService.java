package com.senials.meet.service;

import com.senials.common.mapper.MeetMapper;
import com.senials.common.mapper.MeetMapperImpl;
import com.senials.meet.dto.MeetDTO;
import com.senials.meet.entity.Meet;
import com.senials.meet.repository.MeetRepository;
import com.senials.partyboard.entity.PartyBoard;
import com.senials.partyboard.repository.PartyBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetService {

    private final MeetMapper meetMapper;

    private final PartyBoardRepository partyBoardRepository;

    private final MeetRepository meetRepository;

  
    @Autowired
    public MeetService(
            MeetMapperImpl meetMapperImpl
            , PartyBoardRepository partyBoardRepository
            , MeetRepository meetRepository
    ) {
        this.meetMapper = meetMapperImpl;
        this.partyBoardRepository = partyBoardRepository;
        this.meetRepository = meetRepository;
    }


    /* 모임 내 일정 전체 조회 */
    public List<MeetDTO> getMeetsByPartyBoardNumber(int partyBoardNumber) {

        PartyBoard partyBoard = partyBoardRepository.findById(partyBoardNumber)
                .orElseThrow(IllegalArgumentException::new);

        List<Meet> meetList = meetRepository.findAllByPartyBoardOrderByMeetNumberDesc(partyBoard);

        List<MeetDTO> meetDTOList = meetList.stream().map(meetMapper::toMeetDTO).toList();

        return meetDTOList;
    }


    /* 모임 일정 추가 */
    @Transactional
    public void registerMeet(int partyBoardNumber, MeetDTO meetDTO) {

        PartyBoard partyBoard = partyBoardRepository.findById(partyBoardNumber)
                .orElseThrow(IllegalArgumentException::new);

        Meet meet = meetMapper.toMeet(meetDTO);

        /* PartyBoard 엔티티 연결 */
        meet.initializePartyBoard(partyBoard);

        meetRepository.save(meet);
    }

  
    public List<MeetDTO> getMeetsByUserNumber(int userNumber) {
        List<Meet> meets = meetRepository.findAllByUserNumber(userNumber);
        return meets.stream()
                .map(meet -> new MeetDTO(
                        meet.getMeetNumber(),
                        meet.getPartyBoard().getPartyBoardNumber(),
                        meet.getMeetStartDate(),
                        meet.getMeetEndDate(),
                        meet.getMeetStartTime(),
                        meet.getMeetFinishTime(),
                        meet.getMeetEntryFee(),
                        meet.getMeetLocation(),
                        meet.getMeetMaxMember()
                ))
                .collect(Collectors.toList());
    }

}
