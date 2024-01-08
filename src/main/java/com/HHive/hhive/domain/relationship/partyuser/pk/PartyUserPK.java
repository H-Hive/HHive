/*package com.HHive.hhive.domain.relationship.partyuser.pk;

import com.HHive.hhive.domain.party.entity.Party;
import com.HHive.hhive.domain.user.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}*/
