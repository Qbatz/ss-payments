package com.qbatz.payment.service;

import com.qbatz.payment.repositories.HostelPlanRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostelPlanService {

    @Autowired
    private HostelPlanRepositories hostelPlanRepositories;
}
