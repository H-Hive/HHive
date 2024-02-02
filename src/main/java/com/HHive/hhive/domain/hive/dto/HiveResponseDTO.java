package com.HHive.hhive.domain.hive.dto;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String majorCategory;

    private String subCategory;

    private String roadAddress;

    private String introduction;

    private Long hostId;

    private String hostName;

    private List<String> hivePlayers;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifiedAt;

    public static HiveResponseDTO of(Hive hive) {
        String majorName =
                hive.getMajorCategory() == null ? "" : hive.getMajorCategory().getTitle();

        String subName = hive.getSubCategory() == null ? "" : hive.getSubCategory().getTitle();

        return HiveResponseDTO.builder()
                .id(hive.getId())
                .title(hive.getTitle())
                .majorCategory(majorName)
                .subCategory(subName)
                .roadAddress(hive.getRoadAddress())
                .introduction(hive.getIntroduction())
                .hostId(hive.getCreatorId())
                .hostName(hive.getUser().getUsername())
                .createdAt(hive.getCreatedAt())
                .modifiedAt(hive.getModifiedAt())
                .build();
    }
}
