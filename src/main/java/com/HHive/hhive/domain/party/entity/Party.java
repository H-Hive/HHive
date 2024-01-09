package com.HHive.hhive.domain.party.entity;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.party.request.PartyRequestDto;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Party extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1500)
    private String content;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "host_id", nullable = false)
    private Long hostId;

    @ManyToOne
    @JoinColumn(name = "hive_id", nullable = false)
    private Hive hive;

    @Column
    private String partyTitle;

    @Column
    private String username;

    @Column
    private String partyContent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder

    public Party(String partyTitle,String username, String partyContent) {
        this.partyTitle = partyTitle;
        this.username = username;
        this.partyContent = partyContent;
    }

    public Party(Hive hive, PartyRequestDto dto, User user) {
        this.hive = hive;
        this.partyTitle = dto.getPartyTitle();
        this.username = user.getUsername();
        this.partyContent = dto.getPartyContent();
        this.hostId = user.getId();
    }

    // 연관관계 메서드
    public void setUser(User user) {
        this.user = user;
    }

    // 서비스 메서드
    public void setTitle(String partyTitle) {
        this.partyTitle = partyTitle;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setContent(String partyContent) {
        this.partyContent = partyContent;
    }
}
