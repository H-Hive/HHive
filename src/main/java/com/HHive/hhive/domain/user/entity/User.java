package com.HHive.hhive.domain.user.entity;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import com.HHive.hhive.domain.user.dto.HobbyCategoryRequestDTO;
import com.HHive.hhive.domain.user.dto.UpdateUserProfileRequestDTO;
import com.HHive.hhive.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "\"USER\"")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int social_type;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean is_deleted = false;
    private LocalDateTime deletedAt;

    private Long kakaoId;

    //TODO: 이미지

    //TODO: 다중 카테고리 선택

    @Enumerated(EnumType.STRING)
    private MajorCategory majorCategory;

    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;


    public User(String username, String password, String email, String description) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
    }

    // 카카오
    public User(String username, String password, String email, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.kakaoId = kakaoId;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void updateProfile(UpdateUserProfileRequestDTO requestDTO) {
        this.email = requestDTO.getEmail();
        this.description = requestDTO.getDescription();
    }

    public void updateDeletedAt() {
        this.is_deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void setPassword(String updatePassword) {
        this.password = updatePassword;
    }

    public void setMajorCategory(MajorCategory majorCategory) {
        this.majorCategory = majorCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }
}
