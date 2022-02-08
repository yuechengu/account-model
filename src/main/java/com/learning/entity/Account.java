package com.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private String accountId;
    private BigDecimal balance;
    private String accountType; // currency only; token; gift
    private Integer status; // 0 --invalid; 1 --valid
    private Date createdTime;
    private Date expiredTime;
}
