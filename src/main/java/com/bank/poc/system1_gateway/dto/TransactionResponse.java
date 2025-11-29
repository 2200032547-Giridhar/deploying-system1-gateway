package com.bank.poc.system1_gateway.dto;

import lombok.Data;

@Data
public class TransactionResponse {
    private String status;    // SUCCESS / FAILED
    private String message;   // reason
    private Double balance;   // current balance (optional)
}
