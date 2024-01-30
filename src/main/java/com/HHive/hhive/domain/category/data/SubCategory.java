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
    BASEBALL(MajorCategory.SPORTS, "야구"),

    //TRAVEL
    CAMPING(MajorCategory.TRAVEL,"캠핑"),
    GLAMPING(MajorCategory.TRAVEL,"글램핑"),

    //MUSIC
    BAND(MajorCategory.MUSIC,"밴드"),
    PIANO(MajorCategory.MUSIC,"피아노 동호회"),

    //DANCE
    KPOP(MajorCategory.DANCE,"케이팝 댄스"),
    BELLY(MajorCategory.DANCE,"벨리 댄스"),

    //SOCIAL
    STOCK(MajorCategory.SOCIAL,"주식"),
    BITCOIN(MajorCategory.SOCIAL,"가상 화폐"),

    //MEDIA
    PHOTO(MajorCategory.MEDIA,"사진 동호회"),
    MOVIE(MajorCategory.MEDIA,"영화 동호회"),

    //PET
    DOG(MajorCategory.PET,"강아지,개 모임"),
    CAT(MajorCategory.PET,"고양이 모임");

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
    public static SubCategory findByTitle(String title) {
        return Arrays.stream(SubCategory.values())
                .filter(subCategory -> subCategory.title.equals(title))
                .findFirst()
                .orElse(null);
    }
}
