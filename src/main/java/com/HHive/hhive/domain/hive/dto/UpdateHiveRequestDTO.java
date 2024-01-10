package com.HHive.hhive.domain.hive.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHiveRequestDTO {

    private String title;

    private String introduction;

}
