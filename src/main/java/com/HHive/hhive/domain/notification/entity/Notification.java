package com.HHive.hhive.domain.notification.entity;

import com.HHive.hhive.global.auditing.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "notification")
@NoArgsConstructor
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "group_name")
    private String groupName;

    @Builder
    public Notification(String message) {
        this.message = message;
        this.groupName = "group";
    }

    public void setGroupName(String groupName){
        this.groupName=groupName;
    }



}
