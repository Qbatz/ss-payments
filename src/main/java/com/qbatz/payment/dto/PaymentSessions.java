package com.qbatz.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentSessions {
    @JsonProperty("code")
    private int code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("payments_session")
    private PaymentSession paymentSession;

    @Data
    public class PaymentSession {
        @JsonProperty("payments_session_id")
        private String paymentsSessionId;
        @JsonProperty("amount")
        private String amount;
    }
}
