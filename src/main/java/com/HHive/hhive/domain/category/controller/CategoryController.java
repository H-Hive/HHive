package com.HHive.hhive.domain.category.controller;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import com.HHive.hhive.domain.category.service.CategoryService;
import com.HHive.hhive.domain.user.UserDetailsImpl;
import com.HHive.hhive.global.common.CommonResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<CommonResponse<Map<String, List<String>>>> getAllCategories(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        Map<String, List<String>> categories = categoryService.getAllCategories();

        return ResponseEntity.ok().body(CommonResponse.of(200, "카테고리 조회 성공", categories));
    }

}
