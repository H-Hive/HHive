package com.HHive.hhive.domain.relationship.partyuser.entity;

import com.HHive.hhive.domain.party.entity.Party;

import com.HHive.hhive.domain.relationship.partyuser.pk.PartyUserPK;
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
@NoArgsConstructor
@Getter
public class PartyUser {
    @EmbeddedId
    private PartyUserPK partyUserPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    @MapsId("partyId")
    private Party party;

    @Builder
    public PartyUser(User user , Party party){
        this.user= user;
        this.party=party;
        this.partyUserPK=PartyUserPK.builder()
                .userId(user.getId())
                .partyId(party.getId())
                .build();
    }
}