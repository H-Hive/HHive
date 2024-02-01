package com.HHive.hhive.domain.relationship.partyuser.service;

import com.HHive.hhive.domain.party.dto.MemberResponseDTO;
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

    // Hive 가입 여부 확인 메서드
    public boolean isUserMemberOfHive(Long userId, Long hiveId) {
        return hiveUserRepository.existsByUserIdAndHiveId(userId, hiveId);
    }

    public void addPartyUser(User user, Party party) {
        PartyUser partyUser = new PartyUser(user, party);
        partyUserRepository.save(partyUser);
    }

    public void removePartyUser(Long userId, Long partyId) {
        PartyUserPK partyUserPK = new PartyUserPK(userId, partyId);
        partyUserRepository.deleteById(partyUserPK);
    }

    public List<MemberResponseDTO> getPartyMembers(Long partyId) {
        return partyUserRepository.findUsersByPartyId(partyId).stream()
                .map(partyUser -> new MemberResponseDTO(partyUser.getUser().getUsername(), partyUser.getUser().getEmail()))
                .collect(Collectors.toList());
    }

}