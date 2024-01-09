package com.HHive.hhive.domain.relationship.partyuser.pk;

import com.HHive.hhive.domain.party.entity.Party;
import com.HHive.hhive.domain.user.entity.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class PartyUserPK implements Serializable {

    @JoinColumn(name = "party_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Party party;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}
