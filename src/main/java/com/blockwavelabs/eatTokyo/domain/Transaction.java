package com.blockwavelabs.eatTokyo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_index")
    private User user;

    private String salt;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    private String escrowId;

    private String link;

    private String hashedPassword;

    @ColumnDefault("false")
    private Boolean IsWrappedReceived;

    @Builder
    public Transaction(User user, String salt, String escrowId, String link, String hashedPassword) {
        this.user = user;
        this.salt = salt;
        this.escrowId = escrowId;
        this.link = link;
        this.hashedPassword = hashedPassword;
    }

    @Builder
    public Transaction(User user, String salt, String hashedPassword) {
        this.user = user;
        this.salt = salt;
        this.hashedPassword = hashedPassword;
    }
}
