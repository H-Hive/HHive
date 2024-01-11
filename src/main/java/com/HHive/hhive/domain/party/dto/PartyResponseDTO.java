package com.HHive.hhive.domain.party.dto;

import com.HHive.hhive.domain.party.entity.Party;
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
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private List<MemberResponseDTO> members;


    public PartyResponseDTO(Party party) {
        this.id = party.getId();
        this.title = party.getTitle();
        this.username = party.getUsername();
        this.content = party.getContent();
        this.createAt = party.getCreatedAt();
        this.modifiedAt = party.getModifiedAt();
    }
}
