package com.blockwavelabs.eatTokyo;

import com.blockwavelabs.eatTokyo.domain.Transaction;
import com.blockwavelabs.eatTokyo.dto.*;
import com.blockwavelabs.eatTokyo.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class Controller {
    private final HashingUtil hashingUtil;
    private final PointService pointService;

//    @GetMapping("/points")
//    public ResponseEntity<ResultDto<List<PointInfoDto>>> getPoints(@RequestParam(value = "user_email") String userEmail) throws Exception {
//        List<PointInfoDto> pointInfoDtos = pointService.getAllPoints(userEmail);
//        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), pointInfoDtos));
//    }
//
//    @GetMapping("/points/total")
//    public ResponseEntity<ResultDto<Double>> getSumPoints(@RequestParam(value = "user_email") String userEmail) throws Exception {
//        Double sumPoints = pointService.getSumPoints(userEmail);
//        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), sumPoints));
//    }

    @PostMapping("/points")
    public ResponseEntity<ResultDto<PointInfoDto>> addPoints(@RequestBody PointAddDto pointAddDto) throws Exception {
        PointInfoDto pointInfoDto = pointService.addPoints(pointAddDto);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), pointInfoDto));
    }

    @GetMapping("/txn/hash")
    public ResponseEntity<ResultDto<String>> createTxnHash(@RequestParam(value = "user_email") String userEmail) throws Exception {
        String salt = hashingUtil.generateSalt();
        String hashedPassword = hashingUtil.hash(userEmail, salt);
        pointService.createTxnBySaltHash(salt, userEmail, hashedPassword);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), hashedPassword));
    }

    @PostMapping("/txn/link")
    public ResponseEntity<ResultDto<String>> createTxnLink(@RequestParam(value = "user_email") String userEmail, @RequestBody CreateTxnLinkDto createTxnLinkDto) throws Exception {
        boolean exist = pointService.existsByHashedPassword(createTxnLinkDto.getHashedPassword());
        if(!exist){
            return ResponseEntity.notFound().build();
        }
        String link = hashingUtil.hash(createTxnLinkDto.getEscrowId(), createTxnLinkDto.getHashedPassword());
        pointService.updateLinkEscrowIdByHashPassword(link, createTxnLinkDto.getEscrowId(), createTxnLinkDto.getHashedPassword());
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), link));
    }

    @PostMapping("/txn/check-user")
    public ResponseEntity<ResultDto<String>> checkUser(@RequestParam(value = "user_email") String userEmail, @RequestBody Map<String, String> map){
        String escrowId = map.get("escrowId");
        boolean exist = pointService.existsByEscrowId(escrowId);
        System.out.println(exist);
        System.out.println(escrowId);
        if(!exist){
            return ResponseEntity.notFound().build();
        }
        Transaction txn = pointService.getTransactionByEscrowId(escrowId);
        String rightEmail = txn.getUser().getEmail();
        if(!rightEmail.equals(userEmail)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), txn.getHashedPassword()));
    }

    @GetMapping("/txn/escrow-id")
    public ResponseEntity<ResultDto<String>> getEscrowId(@RequestParam(value = "link") String link) throws Exception {
        boolean exist = pointService.existsByLink(link);
        if(!exist){
            return ResponseEntity.notFound().build();
        }
        Transaction txn = pointService.getTransactionByLink(link);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), txn.getEscrowId()));
    }

    @GetMapping("txn/check/wrapped")
    public ResponseEntity<ResultDto<Boolean>> checkWrapped(@RequestParam(value = "escrowId") String escrowId) throws Exception {
        boolean exist = pointService.existsByEscrowId(escrowId);
        if(!exist){
            return ResponseEntity.notFound().build();
        }
        Transaction txn = pointService.getTransactionByEscrowId(escrowId);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), txn.getIsWrappedReceived()));
    }

    @GetMapping("txn/toggle/wrapped")
    public ResponseEntity<ResultDto<TransactionInfoDto>> toggleWrapped(@RequestParam(value = "escrowId") String escrowId, @RequestParam("toggle") Boolean toggle) throws Exception {
        boolean exist = pointService.existsByEscrowId(escrowId);
        if(!exist){
            return ResponseEntity.notFound().build();
        }
        Transaction txn = pointService.getTransactionByEscrowId(escrowId);
        pointService.toggleIsWrappedReceivedByEscrowId(escrowId, toggle);
        return ResponseEntity.ok(ResultDto.res(HttpStatus.OK, HttpStatus.OK.toString(), TransactionInfoDto.of(txn)));
    }





}
