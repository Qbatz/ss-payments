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

    @Autowired
    private com.qbatz.payment.config.Authentication authentication;

    public PaymentSessions addPaymentSession(String sessionId, Double amount, String hostelId, Double discountAmount, Double planAmount, String planCode) {
        PaymentSessions paymentSessions = new PaymentSessions();
        paymentSessions.setPaymentSessionId(sessionId);
        paymentSessions.setPaymentAmount(amount);
        paymentSessions.setDiscountAmount(discountAmount);
        paymentSessions.setPlanAmount(planAmount);
        paymentSessions.setPlanCode(planCode);
        paymentSessions.setHostelId(hostelId);
        paymentSessions.setCreatedAt(new java.util.Date());
        paymentSessions.setCreatedBy(authentication.getName());
        paymentSessions.setPaymentStaus(OrderStatus.CREATED.name());
        return paymentSessionRepositories.save(paymentSessions);
    }

    public PaymentSessions updatePaymentSession(String paymentsSessionId) {
        PaymentSessions paymentSessions = paymentSessionRepositories.findByPaymentSessionId(paymentsSessionId);
        paymentSessions.setPaymentStaus(OrderStatus.PAID.name());

        return paymentSessionRepositories.save(paymentSessions);
    }

    public PaymentSessions getPaymentSessionBySessionId(String sessionId) {
        return paymentSessionRepositories.findByPaymentSessionId(sessionId);
    }
}
