package com.HHive.hhive.domain.hive.dto;

import com.HHive.hhive.domain.hive.entity.Hive;
import com.HHive.hhive.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HiveCreateRequestDTO {

    @NotBlank
    private String title;

    public Hive toEntity(User createdBy) {
        return Hive.builder()
                .title(title)
                .creatorId(createdBy.getId())
                .introduction("내용을 넣어주세요")
                .user(createdBy)
                .build();
    }

}
