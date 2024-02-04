package com.HHive.hhive.domain.party.repository;

import com.HHive.hhive.domain.party.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartyRepository extends JpaRepository<Party, Long> {

    List<Party> findByUserIdAndIsDeletedIsFalse(Long userId);

}
