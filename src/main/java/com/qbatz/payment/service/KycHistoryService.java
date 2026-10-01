package com.qbatz.payment.service;

import com.qbatz.payment.dao.KycHistory;
import com.qbatz.payment.repositories.KycHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KycHistoryService {

    @Autowired
    private KycHistoryRepository kycHistoryRepository;

    public KycHistory getLatestByHostelId(String hostelId) {
        return kycHistoryRepository.findTopByHostelIdOrderByHistoryIdDesc(hostelId);
    }

    public void save(KycHistory kycHistory) {
        kycHistoryRepository.save(kycHistory);
    }
}
