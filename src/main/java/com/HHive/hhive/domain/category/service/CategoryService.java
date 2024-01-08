package com.HHive.hhive.domain.category.service;


import com.HHive.hhive.domain.category.data.Category;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    public Map<String, List<String>> getAllCategories() {

        return Category.getAllCategories();
    }
}
