package com.HHive.hhive.domain.party.entity;

import com.HHive.hhive.domain.hive.Hive;
import com.HHive.hhive.domain.user.User;
import com.HHive.hhive.global.auditing.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
