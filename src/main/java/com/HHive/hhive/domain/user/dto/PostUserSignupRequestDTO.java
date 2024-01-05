package com.HHive.hhive.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PostUserSignupRequestDTO {

    @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$",
            message = "올바른 형식이 아닙니다. 문자(대문자/소문자) 혹은 숫자를 4글자 이상 10글자 이하로 작성해주세요.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{4,}$",
            message = "올바른 형식이 아닙니다. 문자(대문자/소문자) 혹은 숫자를 4글자 이상 작성해주세요.")
    private String password;

    private String checkPassword;

    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,})+$",
            message = "올바른 이메일 형식이 아닙니다. 문자(대문자/소문자)@도메인으로 입력해주세요.")
    private String email;

    private String description;
}
