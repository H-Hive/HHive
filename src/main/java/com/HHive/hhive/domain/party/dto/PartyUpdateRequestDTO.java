package com.HHive.hhive.domain.party.dto;

import lombok.Getter;

@Getter
public class PartyUpdateRequestDTO {

    private String title;
    private String content;
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hours;
    private Integer minutes;

}

