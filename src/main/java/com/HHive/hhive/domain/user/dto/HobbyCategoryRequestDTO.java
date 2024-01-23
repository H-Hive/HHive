package com.HHive.hhive.domain.user.dto;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HobbyCategoryRequestDTO {

    private String majorCategory;
    private String  subCategory;
}
