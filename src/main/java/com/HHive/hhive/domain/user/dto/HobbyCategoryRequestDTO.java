package com.HHive.hhive.domain.user.dto;

import lombok.Getter;

@Getter
public class HobbyCategoryRequestDTO {

    private String majorCategory;
    private String subCategory;

    public void setMajorCategory(String majorCategory) {
        this.majorCategory = majorCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
