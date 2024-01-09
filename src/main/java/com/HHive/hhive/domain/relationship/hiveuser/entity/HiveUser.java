package com.HHive.hhive.domain.relationship.hiveuser.entity;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.user.entity.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor

public class HiveUser {

    @EmbeddedId
    private HiveUserPK hiveUserPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hive_id")
    @MapsId("hiveId")
    private Hive hive;

    @Builder
    public HiveUser(User user , Hive hive){
        this.user= user;
        this.hive=hive;
        this.hiveUserPK=HiveUserPK.builder()
                .userId(user.getId())
                .hiveId(hive.getId())
                .build();
    }
}
