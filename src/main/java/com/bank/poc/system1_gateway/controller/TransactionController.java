package com.bank.poc.system1_gateway.controller;

import com.bank.poc.system1_gateway.dto.TransactionRequest;
import com.bank.poc.system1_gateway.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/gateway")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    private final RestTemplate restTemplate;

    @PostMapping("/transaction")
    public TransactionResponse handle(@RequestBody TransactionRequest req) {

        // 1. Validation: cardNumber, pin, amount, type must be present
        if (req.getCardNumber() == null || req.getCardNumber().isBlank()
                || req.getPin() == null || req.getPin().isBlank()
                || req.getType() == null || req.getType().isBlank()) {

            TransactionResponse r = new TransactionResponse();
            r.setStatus("FAILED");
            r.setMessage("Missing required fields");
            return r;
        }

        // 2. Validate amount > 0
        if (req.getAmount() <= 0) {
            TransactionResponse r = new TransactionResponse();
            r.setStatus("FAILED");
            r.setMessage("Amount must be > 0");
            return r;
        }

        // 3. Validate type
        if (!req.getType().equals("withdraw") && !req.getType().equals("topup")) {
            TransactionResponse r = new TransactionResponse();
            r.setStatus("FAILED");
            r.setMessage("Invalid type. Must be 'withdraw' or 'topup'");
            return r;
        }

        // 4. Routing logic: only cards starting with '4'
        if (!req.getCardNumber().startsWith("4")) {
            TransactionResponse r = new TransactionResponse();
            r.setStatus("FAILED");
            r.setMessage("Card range not supported");
            return r;
        }

        // 5. Forward to System 2
        String url = "String url = "https://deploying-system2-corebank.onrender.com/api/core/process";
        return restTemplate.postForObject(url, req, TransactionResponse.class);
    }
}
