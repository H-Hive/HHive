package com.HHive.hhive.domain.relationship.partyuser.repository;

import com.HHive.hhive.domain.party.entity.Party;
import com.HHive.hhive.domain.relationship.partyuser.entity.PartyUser;
import com.HHive.hhive.domain.relationship.partyuser.pk.PartyUserPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartyUserRepository extends JpaRepository<PartyUser, PartyUserPK> {
    @Query("SELECT pu FROM PartyUser pu JOIN FETCH pu.user WHERE pu.party.id = :partyId")
    List<PartyUser> findUsersByPartyId(Long partyId);

    List<PartyUser> findByUserId(Long userId);
    boolean existsByUserIdAndPartyId(Long id, Long partyId);

    boolean existsByPartyUserPK_UserIdAndPartyUserPK_PartyId(Long id, Long id1);

    @Query("SELECT pu.party FROM PartyUser pu WHERE pu.user.id = :userId")
    List<Party> findPartiesByUserId(Long userId);

}
