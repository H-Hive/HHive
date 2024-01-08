package com.HHive.hhive.domain.user.entity;

import com.HHive.hhive.domain.user.dto.UpdateUserProfileRequestDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

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

    //TODO: 이미지

    //TODO: 카테고리


    public User(String username, String password, String email, String description) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
    }

    public void updateProfile(UpdateUserProfileRequestDTO requestDTO) {
        this.email = email;
        this.description = description;
    }
}
