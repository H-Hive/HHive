package com.HHive.hhive.domain.party.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class PartyResponseDTO {
    private Long id;
    private String title;
    private String username;
    private String content;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 / HH시 mm분")
    private LocalDateTime dateTime; // 약속 날짜,시간
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedAt;
    private List<MemberResponseDTO> members;

}
