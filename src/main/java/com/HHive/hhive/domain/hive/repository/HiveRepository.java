package com.HHive.hhive.domain.hive.repository;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import com.HHive.hhive.domain.hive.entity.Hive;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HiveRepository extends JpaRepository<Hive, Long> {

    Optional<Hive> findByIdAndIsDeletedIsFalse(Long hive_id);

    Optional<Hive> findByTitle(String title);

    @Query("SELECT h FROM Hive h WHERE h.isDeleted = false")
    List<Hive> findAllHiveNotDeleted();

    @Query("SELECT h FROM Hive h WHERE (:majorCategory is null OR h.majorCategory = :majorCategory)"
            + "AND (:subCategory is null OR h.subCategory = :subCategory) "
            + "AND (:majorCategory is not null OR :subCategory is not null)")
    List<Hive> findAllByMajorCategoryAndSubCategoryContaining(
            @Param("majorCategory") MajorCategory majorCategory,
            @Param("subCategory")SubCategory subCategory
    );
}