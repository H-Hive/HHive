package com.HHive.hhive.domain.party.dto;

import lombok.Getter;

@Getter
public class PartyRequestDTO {

    private String title;
    private String content;
    private int year;
    private int month;
    private int day;
    private int hours;
    private int minutes;

}
