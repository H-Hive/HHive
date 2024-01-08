package com.HHive.hhive.domain.hive.dto;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class HiveDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateHiveRequest {

        @NotBlank
        private String name;

        public Hive toEntity(User createdBy) {
            return Hive.builder()
                    .name(name)
                    .creatorId(createdBy.getId())
                    .build();
        }

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateHiveRequest {

        private String name;

        private String introduction;

        private LocalDateTime modifiedAt;

    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;

        private String name;

        private String introduction;

        private Long hostId;

        private String createdBy;

        private List<String> hivePlayers;

        private LocalDateTime createdAt;

        private LocalDateTime modifiedAt;

        public static HiveDTO.Response of(Hive hive, User user) {
            return Response.builder()
                    .id(hive.getId())
                    .name(hive.getName())
                    .introduction(hive.getIntroduction())
                    .createdBy(user.getEmail())
                    .createdAt(hive.getCreatedAt())
                    .modifiedAt(hive.getModifiedAt())
                    .build();
        }


    }
}
