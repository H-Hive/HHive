package com.HHive.hhive.domain.party.request;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class PartyRequestDto {

    private String partyTitle;
    private String partyContent;

    public PartyRequestDto(String partyTitle, String partyContent) {
        this.partyTitle = partyTitle;
        this.partyContent = partyContent;
    }
}