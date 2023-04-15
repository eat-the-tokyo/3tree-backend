package com.blockwavelabs.eatTokyo.dto;

import com.blockwavelabs.eatTokyo.domain.Point;
import com.blockwavelabs.eatTokyo.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionInfoDto {
    private Long id;
    private Long userId;
    private String salt;
    private LocalDateTime createdAt;
    private String escrowId;
    private String link;
    private String hashedPassword;
    private Boolean isWrappedReceived;

    public static TransactionInfoDto of(Transaction txn){
        return TransactionInfoDto.builder()
                .id(txn.getId())
                .userId(txn.getUser().getId())
                .salt(txn.getSalt())
                .createdAt(txn.getCreatedAt())
                .escrowId(txn.getEscrowId())
                .link(txn.getLink())
                .hashedPassword(txn.getHashedPassword())
                .isWrappedReceived(txn.getIsWrappedReceived())
                .build();
    }
}
