package com.learning.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplyTxReqEntity {
    @JsonProperty(value = "origin-accountId")
    private String originAccountId;

    @JsonProperty(value = "destination-accountId")
    private String destinationAccountId;

    @JsonProperty(value = "trasaction-type")
    private String trasactionType;

    private String amount;

    public String getOriginAccountId() {
        return originAccountId;
    }

    public void setOriginAccountId(String originAccountId) {
        this.originAccountId = originAccountId;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public String getTrasactionType() {
        return trasactionType;
    }

    public void setTrasactionType(String trasactionType) {
        this.trasactionType = trasactionType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
