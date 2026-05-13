package com.qbatz.payment.service;

import com.qbatz.payment.dao.PaymentSessions;
import com.qbatz.payment.enumm.OrderStatus;
import com.qbatz.payment.repositories.PaymentSessionRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentSessionService {
    @Autowired
    private PaymentSessionRepositories paymentSessionRepositories;
    public PaymentSessions updatePaymentSession(String paymentsSessionId) {
        PaymentSessions paymentSessions = paymentSessionRepositories.findByPaymentSessionId(paymentsSessionId);
        paymentSessions.setPaymentStaus(OrderStatus.PAID.name());

        return paymentSessionRepositories.save(paymentSessions);
    }
}
