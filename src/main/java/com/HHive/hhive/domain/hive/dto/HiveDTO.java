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
            return Hive.builder().name(name).user(createdBy).build();
        }

    }


    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long id;

        private String name;

        private String introduction;

        private String createdBy;

        private List<String> hivePlayers;

        private LocalDateTime createdAt;

        public static HiveDTO.Response of(Hive hive) {
            return Response.builder()
                    .id(hive.getId())
                    .name(hive.getName())
                    .introduction(hive.getIntroduction())
                    .createdBy(hive.getUser().getEmail())
                    .createdAt(hive.getCreatedAt())
                    .build();
        }


    }
}
