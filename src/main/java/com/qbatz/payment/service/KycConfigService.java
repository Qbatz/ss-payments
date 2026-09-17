package com.qbatz.payment.service;

import com.qbatz.payment.dao.KycConfig;
import com.qbatz.payment.repositories.KycConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KycConfigService {

    @Autowired
    private KycConfigRepository kycConfigRepository;

    public KycConfig getByHostelId(String hostelId) {
        return kycConfigRepository.findByHostelId(hostelId);
    }

    public KycConfig save(KycConfig kycConfig) {
        return kycConfigRepository.save(kycConfig);
    }
}
