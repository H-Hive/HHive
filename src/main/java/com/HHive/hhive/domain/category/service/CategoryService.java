package com.HHive.hhive.domain.category.service;


import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    public Map<String, List<String>> getAllCategories() {

        Map<String, List<String>> allCategories = new HashMap<>();

        for(MajorCategory majorCategory : MajorCategory.values()) {
            List<String> subCategories = new ArrayList<>();

            for(SubCategory subCategory : SubCategory.values()) {
                if(subCategory.getMajorCategory() == majorCategory) {
                    subCategories.add(subCategory.getTitle());
                }
            }

            allCategories.put(majorCategory.getTitle(), subCategories);
        }

        return allCategories;
    }
}
