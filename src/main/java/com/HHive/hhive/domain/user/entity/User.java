package com.HHive.hhive.domain.user.entity;

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

    //TODO: 이미지

    //TODO: 카테고리


    public User(String username, String password, String email, String description) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
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
}
