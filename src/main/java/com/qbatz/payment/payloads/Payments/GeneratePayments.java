package com.qbatz.payment.payloads.Payments;

public record GeneratePayments(Double amount,
                               String currency,
                               String description) {
}
