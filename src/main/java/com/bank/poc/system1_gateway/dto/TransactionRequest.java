package com.bank.poc.system1_gateway.dto;

import lombok.Data;

@Data
public class TransactionRequest {
    private String cardNumber;
    private String pin;
    private double amount;   // primitive to avoid null issues
    private String type;     // "withdraw" or "topup"
}
