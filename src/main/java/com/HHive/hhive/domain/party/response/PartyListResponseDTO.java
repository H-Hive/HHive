package com.HHive.hhive.domain.party.response;

import com.HHive.hhive.domain.user.dto.UserInfoResponseDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class PartyListResponseDTO {

    private UserInfoResponseDTO user;
    private List<PartyResponseDTO> partyList;

    public PartyListResponseDTO(UserInfoResponseDTO user, List<PartyResponseDTO> partyList) {
        this.user =user;
        this.partyList = partyList;
    }
}
