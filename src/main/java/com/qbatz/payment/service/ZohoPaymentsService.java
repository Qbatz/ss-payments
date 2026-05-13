package com.qbatz.payment.service;

import com.qbatz.payment.dao.ZohoPayment;
import com.qbatz.payment.repositories.ZohoPaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZohoPaymentsService {

    @Autowired
    private ZohoPaymentsRepository zohoPaymentsRepository;
    public void inserIntoDb(String receivedSignature, String hmacKey, String payload, String headers) {
        ZohoPayment zohoPayment = new ZohoPayment();
        zohoPayment.setPayloads(payload);
        zohoPayment.setHeaders(headers);
        zohoPayment.setExpectedSignature(hmacKey);
        zohoPayment.setReceivedSignature(receivedSignature);


        zohoPaymentsRepository.save(zohoPayment);

    }
}
