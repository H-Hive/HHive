package com.HHive.hhive.domain.party.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostPartyRequestDTO {

    private String content;

    private Long hostId;

}
