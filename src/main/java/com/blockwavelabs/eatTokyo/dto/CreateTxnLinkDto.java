package com.blockwavelabs.eatTokyo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateTxnLinkDto {
    private String hashedPassword;
    private String escrowId;
}
