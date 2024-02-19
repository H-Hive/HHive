package com.HHive.hhive.domain.relationship.hiveuser.repository;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.relationship.hiveuser.entity.HiveUser;
import com.HHive.hhive.domain.relationship.hiveuser.entity.HiveUserPK;
import com.HHive.hhive.domain.relationship.hiveuser.repository.custom.CustomHiveUserRepository;
import com.HHive.hhive.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HiveUserRepository extends JpaRepository<HiveUser, HiveUserPK>,
        CustomHiveUserRepository {
    @Query("SELECT uh FROM HiveUser uh JOIN FETCH uh.user WHERE uh.hive.id = :hiveId")
    List<HiveUser> findHiveUsersByHiveId(Long hiveId);

    @Query("SELECT count (uh) > 0 FROM HiveUser uh WHERE uh.hive.id = :hiveId and uh.user.id = :userId and uh.user.is_deleted = false ")
    Boolean existsByUser_IdAndHive_IdAndIsDeletedIsFalse(@Param("userId") Long userId, @Param("hiveId") Long hiveId);

    @Query("SELECT  hu.user FROM HiveUser hu WHERE hu.hive.id = :hiveId")
    List<User> findUsersByHiveId(Long hiveId);

    @Query("SELECT hu.user FROM HiveUser hu WHERE hu.hive.id = :hiveId and hu.user.username = :username")
    Optional<User> findUserByHiveIdAndUsername(@Param("hiveId") Long hiveId, @Param("username") String username);

    @Modifying
    @Query("DELETE FROM HiveUser hu WHERE hu.hive.id = :hiveId and hu.user.id = :userId")
    void deleteHiveUserByHiveIdAndUserId(Long hiveId, Long userId);

    @Query("SELECT hu.hive FROM HiveUser hu WHERE hu.user.id = :userId and hu.hive.isDeleted = false ")
    List<Hive> findAllHiveByHiveUser(Long userId);

    boolean existsByUserIdAndHiveId(Long userId, Long hiveId);
}
