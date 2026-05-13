package com.qbatz.payment.repositories;

import com.qbatz.payment.dao.PaymentSessions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentSessionRepositories extends JpaRepository<PaymentSessions, Long> {
    PaymentSessions findByPaymentSessionId(String paymentSessionId);
}
