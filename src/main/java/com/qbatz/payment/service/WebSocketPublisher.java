package com.qbatz.payment.service;

import com.qbatz.payment.responses.payments.PaymentLinks;
import com.qbatz.payment.responses.payments.PaymentStatus;
import com.qbatz.payment.responses.payments.PaymentStatusCardType;
import com.qbatz.payment.responses.payments.ZohoPaymentResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketPublisher {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendUpdate(ZohoPaymentResponse payload) {
        if (payload.linkId() == null) {
            messagingTemplate.convertAndSend("/payments/" + payload.paymentSessionId(), payload);
        }
        else {
            messagingTemplate.convertAndSend("/payments/" + payload.linkId(), payload);
        }

    }

    public void sendCardUpdate(PaymentStatusCardType paymentStatusCardType) {
        messagingTemplate.convertAndSend("/payments/" + paymentStatusCardType.paymentLinkId(), paymentStatusCardType);
    }
}
