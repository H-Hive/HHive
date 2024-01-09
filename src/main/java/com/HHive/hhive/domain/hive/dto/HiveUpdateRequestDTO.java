package com.HHive.hhive.domain.hive.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HiveUpdateRequestDTO {

    private String title;

    private String introduction;

    private LocalDateTime modifiedAt;

}