package com.HHive.hhive.domain.hive.dto;

import com.HHive.hhive.domain.hive.entity.Hive;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HiveResponseDTO {

    private Long id;

    private String title;

    private String introduction;

    private Long hostId;

    private String hostName;

    private List<String> hivePlayers;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public static HiveResponseDTO of(Hive hive) {
        return HiveResponseDTO.builder()
                .id(hive.getId())
                .title(hive.getTitle())
                .introduction(hive.getIntroduction())
                .hostId(hive.getId())
                .hostName(hive.getUser().getEmail())
                .createdAt(hive.getCreatedAt())
                .modifiedAt(hive.getModifiedAt())
                .build();
    }
}
