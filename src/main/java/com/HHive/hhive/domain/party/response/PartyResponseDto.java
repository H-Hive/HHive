package com.HHive.hhive.domain.party.response;

import com.HHive.hhive.domain.party.entity.Party;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class PartyResponseDto {
    private Long id;
    private String postTitle;
    private String username;
    private String postContent;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;


    public PartyResponseDto(Party party) {
        this.id = party.getId();
        this.postTitle = party.getPartyTitle();
        this.username =party.getUsername();
        this.postContent = party.getPartyContent();
        this.createAt = party.getCreatedAt();
        this.modifiedAt=party.getModifiedAt();
    }
}
