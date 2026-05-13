package com.qbatz.payment.responses.payments;

public record PaymentStatusCardType(String paymentLink, String paymentLinkId, String paymentType,
                                    String cardType,
                                    String lastFourDigits,
                                    String visa,
                                    String issuer,
                                    String cardHolderName) {
}
