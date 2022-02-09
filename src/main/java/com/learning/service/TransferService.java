package com.learning.service;

import com.learning.entity.ApplyTxResEntity;

import java.text.ParseException;

public interface TransferService {
    public ApplyTxResEntity transfer(String originAccountId, String destinationAccountId, String trasactionType, String amount) throws ParseException;
}
