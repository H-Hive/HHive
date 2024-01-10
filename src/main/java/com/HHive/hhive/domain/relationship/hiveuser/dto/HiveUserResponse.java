package com.HHive.hhive.domain.relationship.hiveuser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HiveUserResponse {

    private Long id;

    private Long hiveId;

    private String hivePlayers;
}
