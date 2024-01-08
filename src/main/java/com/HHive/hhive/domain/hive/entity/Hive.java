package com.HHive.hhive.domain.hive.entity;

import com.HHive.hhive.domain.category.entity.Category;
import com.HHive.hhive.domain.hive.dto.HiveDTO;
import com.HHive.hhive.domain.user.entity.User;
import com.HHive.hhive.global.auditing.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hives")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hive extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long creatorId;

    @Column(nullable = false)
    private String name;

    @Column
    private String introduction;

    @Column
    @Builder.Default
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public void update(HiveDTO.UpdateHiveRequest updateHiveRequest) {
        if (updateHiveRequest.getName() != null) {
            this.name = updateHiveRequest.getName();
        }

        if (updateHiveRequest.getIntroduction() != null) {
            this.introduction = updateHiveRequest.getIntroduction();
        }
    }
}