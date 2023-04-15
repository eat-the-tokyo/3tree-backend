package com.blockwavelabs.eatTokyo;

import com.blockwavelabs.eatTokyo.dto.PointInfoDto;
import com.blockwavelabs.eatTokyo.dto.ResultDto;
import com.blockwavelabs.eatTokyo.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class Controller {
    private final PointService pointService;

    @GetMapping("/points")
    public ResponseEntity<ResultDto<List<PointInfoDto>>> getPoints(@RequestParam(value = "user_email") String userEmail) throws Exception {
        System.out.println("getIn");
        List<PointInfoDto> pointInfoDtos = pointService.getAllPoints(userEmail);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), pointInfoDtos));
    }

}
