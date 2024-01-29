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
    private boolean emailVerified;

    public UserInfoResponseDTO(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.description = user.getDescription();

        if (user.getMajorCategory() != null) {
            this.majorCategory = user.getMajorCategory().name();
        }
        if (user.getSubCategory() != null) {
            this.subCategory = user.getSubCategory().name();
        }

        this.emailVerified = user.isEmailVerified();
    }
}
