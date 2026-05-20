package com.qbatz.payment.service;

import com.qbatz.payment.config.Authentication;
import com.qbatz.payment.dao.OrderHistory;
import com.qbatz.payment.dao.PaymentSessions;
import com.qbatz.payment.enumm.OrderStatus;
import com.qbatz.payment.enumm.UserType;
import com.qbatz.payment.repositories.OrderHistoryRepository;
import com.qbatz.payment.responses.payments.PaymentLinks;
import com.qbatz.payment.responses.payments.PaymentStatus;
import com.qbatz.payment.responses.payments.PaymentStatusCardType;
import com.qbatz.payment.responses.payments.ZohoPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderHistoryService {
    @Autowired
    private OrderHistoryRepository orderHistoryRepository;
    @Autowired
    private Authentication authentication;
    @Autowired
    private PaymentSessionService paymentSessionService;

    public void createOrder(String hostelId, PaymentLinks details, Double finalAmount, String planCode,
                            Double discountAmount, Double planPrice, String createdBy) {
        String user = authentication.getName() != null ? authentication.getName() : createdBy;

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setCreatedBy(user);
        orderHistory.setCreatedAt(new Date());
        orderHistory.setPlanAmount(planPrice);
        orderHistory.setPlanCode(planCode);
        orderHistory.setTotalAmount(finalAmount);
        orderHistory.setDiscountAmount(discountAmount != null ? discountAmount : 0.0);
        orderHistory.setOrderStatus(OrderStatus.CREATED.name());
        orderHistory.setActive(true);
        orderHistory.setHostelId(hostelId);
        orderHistory.setPaymentUrl(details.paymentLink());
        orderHistory.setPaymentLinkId(details.paymentLinkId());
        orderHistory.setUserType(UserType.OWNER.name());
        orderHistory.setPaidBy(user);

        orderHistoryRepository.save(orderHistory);
    }

    public void successfullPayment(Object payload) {
        ZohoPaymentResponse paymentLinks = (ZohoPaymentResponse) payload;
        if (paymentLinks == null || paymentLinks.linkId() == null) {
            return;
        }

        OrderHistory orderHistory = orderHistoryRepository.findByPaymentLinkId(paymentLinks.linkId());
        if (orderHistory == null) {
            return;
        }
        if (OrderStatus.PAID.name().equalsIgnoreCase(orderHistory.getOrderStatus())) {
            return;
        }

        orderHistory.setPaymentType(paymentLinks.type());
        if (paymentLinks.type() != null && paymentLinks.type().equalsIgnoreCase("UPI")) {
            PaymentStatus status = paymentLinks.upiStatus();
            if (status != null) {
                orderHistory.setChannel(status.channel());
                orderHistory.setUpiId(status.id());
            }
        }
        else if (paymentLinks.type() != null && paymentLinks.type().equalsIgnoreCase("CARD")) {
            PaymentStatusCardType cardType = paymentLinks.cardType();
            if (cardType != null) {
                orderHistory.setCardBrand(cardType.issuer());
                orderHistory.setCardHolderName(cardType.cardHolderName());
                orderHistory.setCardType(cardType.cardType());
                orderHistory.setIssuer(cardType.issuer());
                orderHistory.setChannel("Card");
                orderHistory.setCardNo(cardType.lastFourDigits());
            }
        }

        orderHistory.setOrderStatus(OrderStatus.PAID.name());
        orderHistory.setPaidAt(new Date());
        orderHistoryRepository.save(orderHistory);
    }

    public void successfullMobilePayment(Object payload) {
        ZohoPaymentResponse paymentLinks = (ZohoPaymentResponse) payload;
        if (paymentLinks == null || paymentLinks.paymentSessionId() == null) {
            return;
        }

        String[] hostelIdSessionId = paymentLinks.paymentSessionId().split("-");
        String sessionId = hostelIdSessionId[hostelIdSessionId.length - 1];

        PaymentSessions paymentSessions = paymentSessionService.getPaymentSessionBySessionId(sessionId);
        if (paymentSessions == null) {
            return;
        }

        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setHostelId(paymentSessions.getHostelId());
        orderHistory.setPaymentSessionId(paymentSessions.getPaymentSessionId());
        orderHistory.setDiscountAmount(paymentSessions.getDiscountAmount());
        orderHistory.setPlanAmount(paymentSessions.getPlanAmount());
        orderHistory.setPlanCode(paymentSessions.getPlanCode());
        orderHistory.setTotalAmount(paymentSessions.getPaymentAmount());
        orderHistory.setUserType(UserType.OWNER.name());
        orderHistory.setPaidBy(paymentSessions.getCreatedBy());
        orderHistory.setCreatedAt(new Date());
        orderHistory.setCreatedBy(paymentSessions.getCreatedBy());
        orderHistory.setActive(true);

        orderHistory.setPaymentType(paymentLinks.type());
        if (paymentLinks.type() != null && paymentLinks.type().equalsIgnoreCase("UPI")) {
            PaymentStatus status = paymentLinks.upiStatus();
            if (status != null) {
                orderHistory.setChannel(status.channel());
                orderHistory.setUpiId(status.id());
            }
        }
        else if (paymentLinks.type() != null && paymentLinks.type().equalsIgnoreCase("CARD")) {
            PaymentStatusCardType cardType = paymentLinks.cardType();
            if (cardType != null) {
                orderHistory.setCardBrand(cardType.issuer());
                orderHistory.setCardHolderName(cardType.cardHolderName());
                orderHistory.setCardType(cardType.cardType());
                orderHistory.setIssuer(cardType.issuer());
                orderHistory.setChannel("Card");
                orderHistory.setCardNo(cardType.lastFourDigits());
            }
        }

        orderHistory.setOrderStatus(OrderStatus.PAID.name());
        orderHistory.setPaidAt(new Date());
        orderHistoryRepository.save(orderHistory);
    }
}
