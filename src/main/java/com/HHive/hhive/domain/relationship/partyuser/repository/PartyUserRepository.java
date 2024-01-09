package com.HHive.hhive.domain.relationship.partyuser.repository;

import com.HHive.hhive.domain.relationship.partyuser.entity.PartyUser;
import com.HHive.hhive.domain.relationship.partyuser.pk.PartyUserPK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartyUserRepository extends JpaRepository<PartyUser, PartyUserPK> {
    @Query("SELECT pu FROM PartyUser pu JOIN FETCH pu.user WHERE pu.party.id = :partyId")
    List<PartyUser> findUsersByPartyId(Long partyId);
}
