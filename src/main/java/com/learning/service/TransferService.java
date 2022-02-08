package com.learning.service;

import com.learning.entity.ApplyTxResEntity;

public interface TransferService {
    public ApplyTxResEntity transfer(String originAccountId, String destinationAccountId, String trasactionType, String amount);
}
