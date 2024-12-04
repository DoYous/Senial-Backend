package com.senials.partyboard.service;

import com.senials.hobbyboard.entity.Hobby;
import com.senials.hobbyboard.repository.HobbyRepository;
import com.senials.partyboard.dto.PartyBoardDTOForWrite;
import com.senials.partyboard.entity.PartyBoard;
import com.senials.partyboard.repository.PartyBoardRepository;
import com.senials.partyboardimage.entity.PartyBoardImage;
import com.senials.user.entity.User;
import com.senials.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PartyBoardService {

    private final PartyBoardRepository partyBoardRepository;

    private final UserRepository userRepository;

    private final HobbyRepository hobbyRepository;


    @Autowired
    public PartyBoardService(
            PartyBoardRepository partyBoardRepository
            , UserRepository userRepository
            , HobbyRepository hobbyRepository
    )
    {
        this.partyBoardRepository = partyBoardRepository;
        this.userRepository = userRepository;
        this.hobbyRepository = hobbyRepository;
    }


    /* 모임 글 작성 */
    @Transactional
    public int registerPartyBoard(int userNumber, PartyBoardDTOForWrite newPartyBoardDTO) {

        // 1. userNumber로 User 엔티티 조회
        User user = userRepository.findById(userNumber)
                .orElseThrow(IllegalArgumentException::new);

        // 2. hobbyNumber로 Hobby 엔티티 조회
        Hobby hobby = hobbyRepository.findById(newPartyBoardDTO.getHobbyNumber())
                .orElseThrow(IllegalArgumentException::new);

        // 3. PartyBoardDTOForWrite -> PartyBoard 엔티티 생성
        PartyBoard newPartyBoard = PartyBoard.builder()
                .partyBoardNumber(0)
                .user(user)
                .hobby(hobby)
                .partyBoardName(newPartyBoardDTO.getPartyBoardName())
                .partyBoardDetail(newPartyBoardDTO.getPartyBoardDetail())
                .partyBoardOpenDate(LocalDate.now())
                .build();

        // 4. 이미지 저장
        List<PartyBoardImage> partyBoardImages = new ArrayList<>();
        for (String savedFile : newPartyBoardDTO.getSavedFiles()) {
            // PartyBoardImage 엔티티 생성
            PartyBoardImage partyBoardImage = PartyBoardImage.builder()
                    .partyBoard(newPartyBoard)
                    .partyBoardImg(savedFile)
                    .build();

            partyBoardImages.add(partyBoardImage);
        }

        newPartyBoard.initializeImages(partyBoardImages);

        // 4. 엔티티 저장
        PartyBoard registeredPartyBoard = partyBoardRepository.save(newPartyBoard);
        return registeredPartyBoard.getPartyBoardNumber();
    }

}
