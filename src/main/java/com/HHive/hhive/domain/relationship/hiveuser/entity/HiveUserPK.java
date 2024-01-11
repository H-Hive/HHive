package com.HHive.hhive.domain.relationship.hiveuser.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class HiveUserPK implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "hive_id")
    private Long hiveId;

    @Builder
    public HiveUserPK(Long userId, Long hiveId) {
        this.hiveId = hiveId;
        this.userId = userId;
    }
}