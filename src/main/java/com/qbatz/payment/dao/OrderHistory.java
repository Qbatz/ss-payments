package com.qbatz.payment.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;
    private String hostelId;
    private String paymentUrl;
    private String paymentLinkId;
    private String paymentSessionId;
    private Double discountAmount;
    private Double planAmount;
    private String planCode;
    private String planName;
    private Double totalAmount;
    private String orderStatus;
    private String paymentType;
    private String cardHolderName;
    private String cardType;
    private String cardBrand;
    private String issuer;
    private String cardNo;
    private String channel;
    private String upiId;
    private String userType;
    private String paymentProof;
    private String paidBy;
    private String collectedBy;
    private boolean isActive;
    private Date createdAt;
    private String createdBy;
    @Column(name = "paid_at")
    private Date paidAt;
}
