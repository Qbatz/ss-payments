package com.qbatz.payment.responses.payments;

import com.qbatz.payment.payloads.Hooks.ZohoWebhookRequest;

public record ZohoPaymentResponse(String type,
                                  String status,
                                  String linkId,
                                  String paymentSessionId,
                                  String paymentId,
                                  PaymentStatus upiStatus,
                                  PaymentStatusCardType cardType,
                                  PaymentStatusNetBanking netBankingStatus) {
}
