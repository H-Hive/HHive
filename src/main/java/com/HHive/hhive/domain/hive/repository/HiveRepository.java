package com.HHive.hhive.domain.hive.repository;

import com.HHive.hhive.domain.hive.entity.Hive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiveRepository extends JpaRepository<Hive, Long> {

}
