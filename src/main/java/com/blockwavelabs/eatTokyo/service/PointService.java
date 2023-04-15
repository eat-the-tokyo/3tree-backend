package com.blockwavelabs.eatTokyo.service;

import com.blockwavelabs.eatTokyo.domain.User;
import com.blockwavelabs.eatTokyo.dto.PointInfoDto;
import com.blockwavelabs.eatTokyo.repository.PointRepository;
import com.blockwavelabs.eatTokyo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PointInfoDto> getAllPoints(String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));

        List<PointInfoDto> allPoints = new ArrayList<>();
        pointRepository.findAllByUser(user)
                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()))
                .stream()
                .forEach(Point -> allPoints.add(PointInfoDto.of(Point)));
        return allPoints;
    }
}
