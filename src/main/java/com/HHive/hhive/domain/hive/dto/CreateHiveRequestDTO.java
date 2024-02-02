package com.HHive.hhive.domain.hive.dto;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHiveRequestDTO {

    @NotBlank
    private String title;

    private String majorCategoryName;

    private String subCategoryName;

    private String fullAddress;

    private String roadAddress;

    public Hive toEntity(User createdBy,MajorCategory majorCategory,SubCategory subCategory) {
        return Hive.builder()
                .title(title)
                .majorCategory(majorCategory)
                .subCategory(subCategory)
                .fullAddress(fullAddress)
                .roadAddress(roadAddress)
                .creatorId(createdBy.getId())
                .introduction("내용을 넣어주세요")
                .user(createdBy)
                .build();
    }

}
