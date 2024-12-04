package com.senials.meet.service;

import com.senials.meet.MeetDTO.MeetDTO;
import com.senials.meet.entity.Meet;
import com.senials.meet.repository.MeetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetService {
    private final MeetRepository meetRepository;

    public MeetService(MeetRepository meetRepository) {
        this.meetRepository = meetRepository;
    }

    public List<MeetDTO> getMeetsByUserNumber(int userNumber) {
        List<Meet> meets = meetRepository.findAllByUserNumber(userNumber);
        return meets.stream()
                .map(meet -> new MeetDTO(
                        meet.getMeetNumber(),
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
