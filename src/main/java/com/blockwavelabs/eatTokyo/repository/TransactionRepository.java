package com.blockwavelabs.eatTokyo.repository;

import com.blockwavelabs.eatTokyo.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Transaction t SET t.link = :link, t.escrowId= :escrowId WHERE t.hashedPassword = :hashedPassword")
    void updateLinkEscrowIdByHashPassword(@Param("link") String link, @Param("escrowId") String escrowId, @Param("hashedPassword") String hashedPassword);

    boolean existsByHashedPassword(String hashedPassword);
    boolean existsByEscrowId(String escrowId);

    Transaction getTransactionByEscrowId(String escrowId);

    boolean existsByLink(String link);
    Transaction getTransactionByLink(String link);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Transaction t SET t.IsWrappedReceived = :isWrappedReceived WHERE t.escrowId = :escrowId")
    void toggleIsWrappedReceivedByEscrowId(@Param("escrowId") String escrowId, @Param("isWrappedReceived") boolean isWrappedReceived);
}
