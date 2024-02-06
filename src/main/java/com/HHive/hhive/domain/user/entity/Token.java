package com.HHive.hhive.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String refreshToken;

    @Column
    private boolean expired;

    @Column
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
