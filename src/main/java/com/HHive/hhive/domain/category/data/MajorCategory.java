package com.HHive.hhive.domain.category.data;

import java.util.Arrays;

public enum MajorCategory {

    GAME("게임"),
    SPORTS("스포츠"),
    TRAVEL("아웃도어/여행"),
    MUSIC("음악/악기"),
    DANCE("댄스/무용"),
    SOCIAL("사교/인맥"),
    MEDIA("사진/영상"),
    PET("반려동물");

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
    public static MajorCategory findByTitle(String title) {
        return Arrays.stream(MajorCategory.values())
                .filter(majorCategory -> majorCategory.title.equals(title))
                .findFirst()
                .orElse(null);
    }
}
