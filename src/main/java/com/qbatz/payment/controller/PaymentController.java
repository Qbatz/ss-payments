package com.qbatz.payment.controller;

import com.qbatz.payment.payloads.Hooks.ZohoWebhookRequest;
import com.qbatz.payment.payloads.Payments.GeneratePayments;
import com.qbatz.payment.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v2/payments")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentsService paymentsService;

    @PostMapping("/generate/{hostelId}")
    public ResponseEntity<?> generatePaymentLink(@PathVariable("hostelId") String hostelId, @RequestBody GeneratePayments generatePayments) {
        return paymentsService.generatePaymentsLink(hostelId, generatePayments);
    }

    @PostMapping("/session/{hostelId}")
    public ResponseEntity<?> createPymentSession(@PathVariable("hostelId") String hostelId, @RequestBody GeneratePayments generatePayments) {
        return paymentsService.generatePaymentSession(hostelId, generatePayments);
    }

    @PostMapping("/hook")
    public ResponseEntity<?> getPaymentStatus(@RequestBody ZohoWebhookRequest payload,
                                              @RequestHeader Map<String, String> headers) {
        return paymentsService.getPaymentStatus(payload, headers);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<?> getPaymentStatusByPaymentId(@PathVariable("paymentId") String paymentId) {
        return paymentsService.getPaymentStatusFromZoho(paymentId);
    }

}
