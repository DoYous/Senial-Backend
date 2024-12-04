package com.senials.common.mapper;

import com.senials.partyboard.dto.PartyBoardDTOForDetail;
import com.senials.partyboard.entity.PartyBoard;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PartyBoardMapper {

    PartyBoardDTOForDetail toPartyBoardDTOForDetail(PartyBoard partyBoard);

}
