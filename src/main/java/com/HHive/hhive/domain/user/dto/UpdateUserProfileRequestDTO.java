package com.HHive.hhive.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserProfileRequestDTO {

    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,})+$",
            message = "올바른 이메일 형식이 아닙니다. 문자(대문자/소문자)@도메인으로 입력해주세요.")
    private String email;

    private String description;
}
