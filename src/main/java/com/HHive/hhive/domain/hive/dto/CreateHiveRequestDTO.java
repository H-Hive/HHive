package com.HHive.hhive.domain.hive.dto;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
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

    private MajorCategory majorCategory;

    private SubCategory subCategory;

    public Hive toEntity(User createdBy) {
        return Hive.builder()
                .title(title)
                .majorCategory(majorCategory)
                .subCategory(subCategory)
                .creatorId(createdBy.getId())
                .introduction("내용을 넣어주세요")
                .user(createdBy)
                .build();
    }

}
