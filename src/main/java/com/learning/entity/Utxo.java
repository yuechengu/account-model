package com.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utxo {
    private String transactionId;
    private String utxoId;
    private String subTransaction;
    private BigDecimal amount;
    private String destinationAccountId;
    private Date transactionTime;
    private String hash;
}
