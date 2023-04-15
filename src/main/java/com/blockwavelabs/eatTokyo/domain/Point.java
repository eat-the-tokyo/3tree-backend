package com.blockwavelabs.eatTokyo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_index")
    private User user;

    private Double value;

    private String kind;

    @Builder
    public Point(User user, Double value, String kind) {
        this.user = user;
        this.value = value;
        this.kind = kind;
    }

}
