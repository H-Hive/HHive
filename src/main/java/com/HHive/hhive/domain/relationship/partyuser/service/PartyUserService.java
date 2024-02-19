package com.HHive.hhive.domain.relationship.partyuser.service;

import com.HHive.hhive.domain.party.dto.PartyResponseDTO;
import com.HHive.hhive.domain.party.entity.Party;
import com.HHive.hhive.domain.relationship.hiveuser.repository.HiveUserRepository;
import com.HHive.hhive.domain.relationship.partyuser.entity.PartyUser;
import com.HHive.hhive.domain.relationship.partyuser.pk.PartyUserPK;
import com.HHive.hhive.domain.relationship.partyuser.repository.PartyUserRepository;
import com.HHive.hhive.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional
public class PartyUserService {

    private final HiveUserRepository hiveUserRepository;
    private final PartyUserRepository partyUserRepository;

    @Transactional
    public boolean isUserMemberOfHive(Long userId, Long hiveId) {

        return hiveUserRepository.existsByUserIdAndHiveId(userId, hiveId);
    }
    @Transactional
    public void addPartyUser(User user, Party party) {

        PartyUser partyUser = new PartyUser(user, party);
        partyUserRepository.save(partyUser);
    }
    @Transactional
    public void removePartyUser(Long userId, Long partyId) {

        PartyUserPK partyUserPK = new PartyUserPK(userId, partyId);
        partyUserRepository.deleteById(partyUserPK);
    }
    @Transactional
    public List<PartyResponseDTO> getPartiesJoinedByUser(Long userId) {

        List<PartyUser> partyUsers = partyUserRepository.findByUserId(userId);
        List<Party> parties = partyUsers.stream().map(PartyUser::getParty).collect(Collectors.toList());

        return parties.stream().map(PartyResponseDTO::of).collect(Collectors.toList());
    }

}