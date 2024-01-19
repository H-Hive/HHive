package com.HHive.hhive.domain.category.data;

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
}
