package com.qbatz.payment.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ZohoPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @Column(columnDefinition = "LONGTEXT")
    private String payloads;
    @Column(columnDefinition = "TEXT")
    private String headers;
    private String receivedSignature;
    private String expectedSignature;
}
