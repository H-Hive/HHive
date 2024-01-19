package com.HHive.hhive.domain.hive.repository;

import com.HHive.hhive.domain.hive.entity.Hive;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HiveRepository extends JpaRepository<Hive, Long> {

    Optional<Hive> findByIdAndIsDeletedIsFalse(Long hive_id);

    Optional<Hive> findByTitle(String title);

    @Query("SELECT h FROM Hive h WHERE h.isDeleted = false")
    List<Hive> findAllHiveNotDeleted();

}