package com.learning.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplyTxResEntity {
    private String result;

    @JsonProperty(value = "transaction-time")
    private String transactionTime;

    @JsonProperty(value = "hash-code")
    private String hashCode;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    @JsonProperty(value = "transaction-time")
    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    @JsonProperty(value = "hash-code")
    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }


    public ApplyTxResEntity(String result, String transactionTime, String hashCode) {
        this.result = result;
        this.transactionTime = transactionTime;
        this.hashCode = hashCode;
    }
}
