package com.HHive.hhive.domain.party.response;

import com.HHive.hhive.domain.user.dto.UserInfoResponseDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class PartyListResponseDto {

    private UserInfoResponseDTO user;
    private List<PartyResponseDto> partyList;
    public void PostListResponseDto(UserInfoResponseDTO user, List<PartyResponseDto> partyList) {
        this.user =user;
        this.partyList = partyList;
    }
}
