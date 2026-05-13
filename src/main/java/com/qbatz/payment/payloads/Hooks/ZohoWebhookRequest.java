package com.qbatz.payment.payloads.Hooks;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class ZohoWebhookRequest {

    @JsonProperty("event_id")
    private Long eventId;
    @JsonProperty("event_type")
    private String eventType;
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("live_mode")
    private Boolean liveMode;
    @JsonProperty("event_time")
    private Long eventTime;
    @JsonProperty("event_object")
    private EventObject eventObject;

    @Data
    public static class EventObject {
        @JsonProperty("payment")
        private Payment payment;
    }


    @Data
    public static class Payment {
        @JsonProperty("dialing_code")
        private String dialingCode;
        @JsonProperty("date")
        private Long date;
        @JsonProperty("fraud_alert")
        private String fraudAlert;
        @JsonProperty("payment_link_id")
        private String paymentLinkId;
        @JsonProperty("transaction_reference_number")
        private String transactionReferenceNumber;
        @JsonProperty("description")
        private String description;
        @JsonProperty("statement_descriptor")
        private String statementDescriptor;
        @JsonProperty("payment_id")
        private String paymentId;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("payments_session_id")
        private String paymentsSessionId;
        @JsonProperty("tip")
        private String tip;
        @JsonProperty("invoice_number")
        private String invoiceNumber;
        @JsonProperty("amount")
        private String amount;
        @JsonProperty("failure_code")
        private String failureCode;
        private String transactionTypeFormatted;
        private String defaultCurrencyAmount;
        private String transactionType;
        private String referenceNumber;
        private String paymentType;
        private String phone;
        private String receiptEmail;
        private String nextAction;
        private String failureCategory;
        private String defaultCurrency;
        private String status;

        private List<Object> refunds;
        private List<Object> metaData;
        @JsonProperty("payment_method")
        private PaymentMethod paymentMethod;
    }

    @Data
    public static class PaymentMethod {
        @JsonProperty("type")
        private String type;
        @JsonProperty("upi")
        private Upi upi;
        @JsonProperty("card")
        private Card card;
    }

    @Data
    public static class Upi {
        @JsonProperty("account_type")
        private String accountType;
        @JsonProperty("channel")
        private String channel;
        @JsonProperty("upi_id")
        private String upiId;
    }

    @Data
    public static class Card {
        @JsonProperty("expiry_month")
        private String expiryMonth;
        @JsonProperty("funding")
        private String funding;
        @JsonProperty("card_holder_name")
        private String cardHolderName;
        @JsonProperty("last_four_digits")
        private String lastFourDigits;
        @JsonProperty("brand")
        private String brand;
        @JsonProperty("issuer")
        private String issuer;
    }
}
