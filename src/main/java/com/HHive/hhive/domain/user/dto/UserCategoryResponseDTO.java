package com.HHive.hhive.domain.user.dto;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import lombok.Getter;

@Getter
public class UserCategoryResponseDTO {

    private MajorCategory majorCategory;
    private SubCategory subCategory;

    public UserCategoryResponseDTO(MajorCategory majorCategory, SubCategory subCategory) {
        this.majorCategory = majorCategory;
        this.subCategory = subCategory;
    }
}
