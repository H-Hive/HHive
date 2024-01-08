package com.HHive.hhive.domain.category.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public static Map<String, List<String>> getAllCategories() {

        Map<String, List<String>> categories = new HashMap<>();

        for(Category category: Category.class.getEnumConstants()) {
            if(category.getParentCategory().isEmpty()) continue;
            if(category.getParentCategory().get().equals(Category.ROOT)) {
                categories.put(category.getTitle(), new ArrayList<>());
            }
        }

        for(Category category: Category.class.getEnumConstants()) {
            for(String s: categories.keySet()) {
                if(category.getParentCategory().isEmpty()) continue;
                if(category.getParentCategory().get().getTitle().equals(s)) {
                    categories.get(s).add(category.getTitle());
                }
            }
        }

        return categories;
    }
}
