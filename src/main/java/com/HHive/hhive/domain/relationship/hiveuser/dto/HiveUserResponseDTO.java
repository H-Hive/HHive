package com.HHive.hhive.domain.relationship.hiveuser.dto;

import com.HHive.hhive.domain.relationship.hiveuser.entity.HiveUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HiveUserResponseDTO {

    private Long id;

    private Long hiveId;

    private String username;

    private String email;

    public static HiveUserResponseDTO of(HiveUser hiveUser) {
        return HiveUserResponseDTO.builder()
                .id(hiveUser.getUser().getId())
                .hiveId(hiveUser.getHive().getId())
                .username(hiveUser.getUser().getUsername())
                .email(hiveUser.getUser().getEmail())
                .build();


    }
}
