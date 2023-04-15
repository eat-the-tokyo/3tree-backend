package com.blockwavelabs.eatTokyo.dto;

import com.blockwavelabs.eatTokyo.domain.Point;
import com.blockwavelabs.eatTokyo.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointInfoDto {
    private Long id;
    private Double value;
    private String userEmail;

    @Builder
    public PointInfoDto(Long id, Double value, String userEmail) {
        this.id = id;
        this.value = value;
        this.userEmail = userEmail;
    }

    public static PointInfoDto of(Point point){
        return PointInfoDto.builder()
                .id(point.getId())
                .value(point.getValue())
                .userEmail(point.getUser().getEmail())
                .build();
    }
}
