package com.HHive.hhive.domain.user.dto;

import lombok.Getter;

@Getter
public class UpdateUserPasswordRequestDTO {

    private String password;

    private String updatePassword;

    private String checkUpdatePassword;
}
