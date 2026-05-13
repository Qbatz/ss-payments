package com.qbatz.payment.responses.payments;

public record PaymentStatus(String paymentLink, String paymentLinkId, String paymentType,
                            String channel,
                            String id) {
}
