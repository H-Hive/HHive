package com.HHive.hhive.domain.party.entity;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.party.dto.PartyRequestDTO;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Party extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "host_id", nullable = false)
    private Long hostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hive_id", nullable = false)
    private Hive hive;

    @Column
    private String title;

    @Column
    private String username;

    @Column(length = 1500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private LocalDateTime dateTime;  // 약속 시간


    public Party(Hive hive, PartyRequestDTO dto, User user, LocalDateTime dateTime) {
        this.hive = hive;
        this.title = dto.getTitle();
        this.username = user.getUsername();
        this.content = dto.getContent();
        this.hostId = user.getId();
        this.dateTime = dateTime;
    }

    // 연관관계 메서드
    public void setUser(User user) {
        this.user = user;
    }

    // 날짜 설정 메서드
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    // 서비스 메서드
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    public void setTitle(String partyTitle) {
        this.title = partyTitle;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setContent(String partyContent) {
        this.content = partyContent;
    }
}
