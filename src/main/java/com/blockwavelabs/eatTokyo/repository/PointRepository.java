package com.blockwavelabs.eatTokyo.repository;

import com.blockwavelabs.eatTokyo.domain.Point;
import com.blockwavelabs.eatTokyo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<List<Point>> findAllByUser(User user);

    @Query("SELECT SUM(p.value) FROM Point p Where p.user = :user")
    Double sumAllValueByUser(@Param("user") User user);
}
