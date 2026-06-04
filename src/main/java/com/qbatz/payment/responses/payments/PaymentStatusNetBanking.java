package com.qbatz.payment.responses.payments;

public record PaymentStatusNetBanking(
        String bankName,
        String channel
) {
}
