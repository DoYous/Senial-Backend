package com.senials.user.service;

import com.senials.common.mapper.UserMapper;
import com.senials.partyboard.dto.PartyBoardDTOForCard;
import com.senials.partyboard.entity.PartyBoard;
import com.senials.partyboard.repository.PartyBoardRepository;
import com.senials.partymember.entity.PartyMember;
import com.senials.partymember.repository.PartyMemberRepository;
import com.senials.partyreview.entity.PartyReview;
import com.senials.user.dto.UserCommonDTO;
import com.senials.user.dto.UserDTO;
import com.senials.user.entity.User;
import com.senials.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserMapper userMapper;

    private com.senials.user.repository.UserRepository userRepository;

    private final PartyMemberRepository partyMemberRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository, PartyMemberRepository partyMemberRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.partyMemberRepository = partyMemberRepository;
    }

    //모든 사용자 조회
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserDTO)
                .toList();

    }

    // 특정 사용자 조회
    public UserCommonDTO getUserByNumber(int userNumber) {
        return userRepository.findById(userNumber)
                .map(user -> new UserCommonDTO(
                        user.getUserName(),
                        user.getUserNickname(),
                        user.getUserDetail(),
                        user.getUserProfileImg()
                ))
                .orElseThrow(IllegalArgumentException::new);
    }

    //특정 사용자 탈퇴
    public boolean deleteUser(int userNumber) {
        if (userRepository.existsById(userNumber)) {
            userRepository.deleteById(userNumber);
            return true;
        }
        return false; // 사용자 존재하지 않음
    }

    // 특정 사용자 수정
    public boolean updateUserProfile(int userNumber, String userNickname, String userDetail) {
        return userRepository.findById(userNumber).map(existingUser -> {
            if (userNickname != null) {
                existingUser.updateUserNickname(userNickname);
            }
            if (userDetail != null) {
                existingUser.updateUserDetail(userDetail);
            }
            userRepository.save(existingUser);
            return true;
        }).orElseThrow(IllegalArgumentException::new);
    }

    //사용자별 참여한 모임 출력
    // 사용자별 참여한 모임 목록을 PartyBoardDTOForCard로 반환
    public List<PartyBoardDTOForCard> getJoinedPartyBoardsByUserNumber(int userNumber) {
        // 사용자가 참여한 PartyMember 목록 조회
        List<PartyMember> partyMembers = partyMemberRepository.findByUser_UserNumber(userNumber);

        // PartyBoardDTOForCard 리스트로 변환
        return partyMembers.stream()
                .map(pm -> {
                    PartyBoard partyBoard = pm.getPartyBoard();
                    // 별점 평균 계산
                    double averageRating = 0.0;
                    List<PartyReview> reviews = partyBoard.getReviews();
                    if (!reviews.isEmpty()) {
                        int totalRating = reviews.stream().mapToInt(PartyReview::getPartyReviewRate).sum();
                        averageRating = (double) totalRating / reviews.size();
                    }

                    // 첫 번째 이미지 가져오기
                    String firstImage = partyBoard.getImages().isEmpty() ? null : partyBoard.getImages().get(0).getPartyBoardImg();

                    // DTO 생성
                    return new PartyBoardDTOForCard(
                            partyBoard.getPartyBoardNumber(),
                            partyBoard.getPartyBoardName(),
                            partyBoard.getPartyBoardStatus(),
                            partyBoard.getPartyMembers().size(), // 참여 인원 수
                            averageRating,
                            firstImage
                    );
                }).collect(Collectors.toList());
    }
}
