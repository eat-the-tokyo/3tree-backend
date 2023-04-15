package com.blockwavelabs.eatTokyo.service;

import com.blockwavelabs.eatTokyo.domain.Point;
import com.blockwavelabs.eatTokyo.domain.Transaction;
import com.blockwavelabs.eatTokyo.domain.User;
import com.blockwavelabs.eatTokyo.dto.PointAddDto;
import com.blockwavelabs.eatTokyo.dto.PointInfoDto;
import com.blockwavelabs.eatTokyo.repository.PointRepository;
import com.blockwavelabs.eatTokyo.repository.UserRepository;
import com.blockwavelabs.eatTokyo.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

//    @Transactional(readOnly = true)
//    public List<PointInfoDto> getAllPoints(String userEmail) throws Exception {
//        User user = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
//
//        List<PointInfoDto> allPoints = new ArrayList<>();
//        pointRepository.findAllByUser(user)
//                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()))
//                .stream()
//                .forEach(Point -> allPoints.add(PointInfoDto.of(Point)));
//        return allPoints;
//    }

//    @Transactional(readOnly = true)
//    public Double getSumPoints(String userEmail) throws Exception {
//        User user = userRepository.findByEmail(userEmail)
//                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
//
//        return pointRepository.sumAllValueByUser(user);
//    }

    @Transactional
    public PointInfoDto addPoints(PointAddDto pointAddDto) throws Exception {
        User user = userRepository.findByEmail(pointAddDto.getUserEmail())
                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));

        Point point = Point.builder()
                .user(user)
                .value(pointAddDto.getValue())
                .kind(pointAddDto.getKind())
                .build();
        pointRepository.save(point);
        return PointInfoDto.of(point);
    }

    @Transactional
    public void createTxnBySaltHash(String salt, String userEmail, String hashedPassword) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception(HttpStatus.NOT_FOUND.toString()));
        Transaction txn = Transaction.builder()
                        .user(user)
                        .salt(salt)
                        .hashedPassword(hashedPassword)
                        .build();
        transactionRepository.save(txn);
    }

    @Transactional
    public void updateLinkEscrowIdByHashPassword(String link, String escrowId, String hashedPassword){
        transactionRepository.updateLinkEscrowIdByHashPassword(link, escrowId, hashedPassword);
    }

    @Transactional(readOnly = true)
    public Boolean existsByHashedPassword(String hashedPassword){
        return transactionRepository.existsByHashedPassword(hashedPassword);
    }

    @Transactional(readOnly = true)
    public Boolean existsByEscrowId(String escrowId){
        return transactionRepository.existsByEscrowId(escrowId);
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionByEscrowId(String escrowId){
        return transactionRepository.getTransactionByEscrowId(escrowId);
    }

    @Transactional(readOnly = true)
    public Transaction getTransactionByLink(String link){
        return transactionRepository.getTransactionByLink(link);
    }

    @Transactional(readOnly = true)
    public Boolean existsByLink(String link){
        return transactionRepository.existsByLink(link);
    }

    @Transactional
    public void toggleIsWrappedReceivedByEscrowId(String escrowId, boolean toggle){
        transactionRepository.toggleIsWrappedReceivedByEscrowId(escrowId, toggle);
    }

}
