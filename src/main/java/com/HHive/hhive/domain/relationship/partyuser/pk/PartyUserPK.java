package com.HHive.hhive.domain.relationship.partyuser.pk;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class PartyUserPK implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "party_id")
    private Long partyId;

    @Builder
    public PartyUserPK(Long userId,Long partyId){
        this.partyId=partyId;
        this.userId=userId;
    }
}
