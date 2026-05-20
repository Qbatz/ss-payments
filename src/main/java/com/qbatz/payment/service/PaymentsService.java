package com.qbatz.payment.service;

import com.qbatz.payment.Utils;
import com.qbatz.payment.payloads.Hooks.ZohoWebhookRequest;
import com.qbatz.payment.payloads.Payments.GeneratePayments;
import com.qbatz.payment.responses.payments.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class PaymentsService {

    @Autowired
    private ZohoService zohoService;
    @Autowired
    private OrderHistoryService orderHistoryService;
    @Autowired
    private ZohoPaymentsService zohoPaymentsService;
    @Autowired
    private PaymentSessionService paymentSessionService;

    private final WebSocketPublisher publisher;

    public PaymentsService(WebSocketPublisher publisher) {
        this.publisher = publisher;
    }


    public ResponseEntity<?> generatePaymentsLink(String hostelId, GeneratePayments generatePayments) {
        PaymentLinks paymentLink = zohoService.generatePaymentLink(hostelId, generatePayments, 1);

        return new ResponseEntity<>(paymentLink, HttpStatus.OK);
    }

    public ResponseEntity<?> getPaymentStatus(ZohoWebhookRequest payload, Map<String, String> headers) {

//        zohoPaymentsService.inserIntoDb(" ", "hmacKey", payload.getEventType(), headers.toString());
        String header = headers.get("x-zoho-webhook-signature");
        String[] parts = header.split(",");
        String timestamp = parts[0].split("=")[1];
        String receivedSignature = parts[1].split("=")[1];

        String data = timestamp + "." + payload;



        try {
            String hmacKey = Utils.generateHmac(data, "ab2bb549067cedf13695cd09a265582e0e2b8c5d0e2e50ac0d01ee7a803c8afea74e0b387f9ae7d8afa6e3ade26e092addd7ff0c7a97edaa4368bdb6eca52b599db49a8f8c8666015cd39314d4191f2b");
//            zohoPaymentsService.inserIntoDb(receivedSignature, hmacKey, payload, header.toString());

            if (payload.getEventObject().getPayment() != null) {
                String paymentStatus = payload.getEventObject().getPayment().getStatus();
                if (StringUtils.hasText(paymentStatus) && paymentStatus.equalsIgnoreCase("success")) {
                    ZohoWebhookRequest.PaymentMethod paymentMethod = payload.getEventObject().getPayment().getPaymentMethod();
                    if (paymentMethod != null) {
                        String type = paymentMethod.getType();
                        String accountType = null;
                        String channel = null;
                        String upiId = null;
                        if (type.equalsIgnoreCase("upi")) {
                            ZohoWebhookRequest.Upi upi = paymentMethod.getUpi();
                            accountType = upi.getAccountType();
                            channel = upi.getChannel();
                            upiId = upi.getUpiId();

                            PaymentStatus status = new PaymentStatus(payload.getEventObject().getPayment().getPaymentLinkId(),
                                    payload.getEventObject().getPayment().getPaymentLinkId(),
                                    type,
                                    channel,
                                    upiId);
                            if (payload.getEventObject().getPayment().getPaymentLinkId() == null) {
                                com.qbatz.payment.dao.PaymentSessions paymentSessions = paymentSessionService
                                        .updatePaymentSession(payload.getEventObject().getPayment().getPaymentsSessionId());
                                if (paymentSessions != null) {
                                    String eventId = paymentSessions.getHostelId() + "-" + payload.getEventObject().getPayment().getPaymentsSessionId();

                                    ZohoPaymentResponse paymentResponse = new ZohoPaymentResponse(type,
                                            "Success",
                                            payload.getEventObject().getPayment().getPaymentLinkId(),
                                            eventId,
                                            status,
                                            null);
                                    orderHistoryService.successfullMobilePayment(paymentResponse);
                                    publisher.sendUpdate(paymentResponse);
                                }

                            }
                            else {
                                ZohoPaymentResponse paymentResponse = new ZohoPaymentResponse(type,
                                        "Success",
                                        payload.getEventObject().getPayment().getPaymentLinkId(),
                                        null,
                                        status,
                                        null);
                                orderHistoryService.successfullPayment(paymentResponse);
                                publisher.sendUpdate(paymentResponse);
                            }
                        }
                        else if (type.equalsIgnoreCase("card")) {
                            ZohoWebhookRequest.Card card = paymentMethod.getCard();
                            PaymentStatusCardType cardStatus = new PaymentStatusCardType(payload.getEventObject().getPayment().getPaymentLinkId(),
                                    payload.getEventObject().getPayment().getPaymentLinkId(),
                                    type,
                                    card.getFunding(),
                                    card.getLastFourDigits(),
                                    card.getBrand(),
                                    card.getIssuer(),
                                    card.getCardHolderName());
                            ZohoPaymentResponse paymentResponse = new ZohoPaymentResponse(type,
                                    "Success",
                                    payload.getEventObject().getPayment().getPaymentLinkId(),
                                    null,
                                    null,
                                    cardStatus);

                            orderHistoryService.successfullPayment(paymentResponse);
                            publisher.sendUpdate(paymentResponse);
                        }

                    }
//                    PaymentLinks paymentLinks = new PaymentLinks(payload.getEventObject().getPayment().getPaymentLinkId(), payload.getEventObject().getPayment().getPaymentLinkId());

                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<?> generatePaymentSession(String hostelId, GeneratePayments generatePayments) {
        PaymentSessions paymentSessions = zohoService.generatePaymentSessions(hostelId, generatePayments, 1);

        return new ResponseEntity<>(paymentSessions, HttpStatus.OK);
    }

    public ResponseEntity<?> getPaymentStatusFromZoho(String paymentId) {
        return zohoService.getPaymentStatusFromZoho(paymentId, 1);
    }
}
