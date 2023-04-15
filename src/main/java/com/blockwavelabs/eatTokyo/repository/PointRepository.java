package com.blockwavelabs.eatTokyo.repository;

import com.blockwavelabs.eatTokyo.domain.Point;
import com.blockwavelabs.eatTokyo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<List<Point>> findAllByUser(User user);
}
