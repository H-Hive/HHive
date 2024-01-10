package com.HHive.hhive.domain.relationship.hiveuser.repository;

import com.HHive.hhive.domain.relationship.hiveuser.entity.HiveUser;
import com.HHive.hhive.domain.relationship.hiveuser.entity.HiveUserPK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HiveUserRepository extends JpaRepository<HiveUser, HiveUserPK> {
    @Query("SELECT uh FROM HiveUser uh JOIN FETCH uh.user WHERE uh.hive.id = :hiveId")
    List<HiveUser> findUsersByHiveId(Long hiveId);

    @Query("SELECT count (uh) > 0 FROM HiveUser uh WHERE uh.hive.id = :hiveId and uh.user.id = :userId and uh.user.is_deleted = false ")
    Boolean existsByUser_IdAndHive_IdAndIsDeletedIsFasle(@Param("userId") Long userId, @Param("hiveId") Long hiveId);
}
