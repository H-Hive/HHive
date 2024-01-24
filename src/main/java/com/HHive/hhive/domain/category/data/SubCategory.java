package com.HHive.hhive.domain.category.data;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Arrays;

public enum SubCategory {

    //GAME
    LOL(MajorCategory.GAME, "리그 오브 레전드"),
    OVERWATCH(MajorCategory.GAME, "오버워치"),
    STARCRAFT(MajorCategory.GAME, "스타크래프트"),

    //SPORTS
    SOCCER(MajorCategory.SPORTS, "축구"),
    BASEBALL(MajorCategory.SPORTS, "야구");

    @Enumerated(EnumType.STRING)
    private final MajorCategory majorCategory;
    private final String title;

    SubCategory(MajorCategory majorCategory, String title) {
        this.majorCategory = majorCategory;
        this.title = title;
    }

    public MajorCategory getMajorCategory() {
        return majorCategory;
    }

    public String getTitle() {
        return title;
    }

    public static SubCategory findByStringName(String categoryName) {
        return Arrays.stream(SubCategory.values()).filter(subCategory -> subCategory.name().equals(categoryName)).findFirst().orElse(null);
    }
}
