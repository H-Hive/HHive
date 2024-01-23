package com.HHive.hhive.domain.user.dto;

import com.HHive.hhive.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponseDTO {

    private Long userId;
    private String username;
    private String email;
    private String description;
    private String majorCategory;
    private String subCategory;

    public UserInfoResponseDTO(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.description = user.getDescription();
        this.majorCategory = user.getMajorCategory().name();
        this.subCategory = user.getSubCategory().name();
    }
}
