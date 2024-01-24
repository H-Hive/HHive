package com.HHive.hhive.domain.category.data;

import java.util.Arrays;

public enum MajorCategory {

    GAME("게임"),
    SPORTS("스포츠");

    private final String title;

    MajorCategory(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static MajorCategory findByStringName(String categoryName) {
        return Arrays.stream(MajorCategory.values()).filter(majorCategory -> majorCategory.name().equals(categoryName)).findFirst().orElse(null);
    }
}
