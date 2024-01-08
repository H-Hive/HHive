package com.HHive.hhive.global.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;

public enum Category {

    ROOT("Category", null),
        GAME("Game", ROOT),
            LOL("League of Legends", GAME),
            OVERWATCH("Overwatch", GAME),
            STARCRAFT("StarCraft", GAME),
        SPORTS("Sports", ROOT),
            SOCCER("Soccer", SPORTS),
            BASEBALL("Baseball", SPORTS);

    @Getter
    private final String title;
    private final Category parentCategory;
    @Getter
    private final List<Category> childCategories;

    Category(String title, Category parentCategory) {
        this.childCategories = new ArrayList<>();
        this.title = title;
        this.parentCategory = parentCategory;
        if(Objects.nonNull(parentCategory)) parentCategory.childCategories.add(this);
    }

    public Optional<Category> getParentCategory() {
        return Optional.ofNullable(parentCategory);
    }
}
