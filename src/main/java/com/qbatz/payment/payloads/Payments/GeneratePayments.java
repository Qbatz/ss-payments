package com.qbatz.payment.payloads.Payments;

public record GeneratePayments(Double amount,
                               String currency,
                               String description,
                               String planCode,
                               Double discountAmount,
                               Double planPrice,
                               String createdBy) {
}
