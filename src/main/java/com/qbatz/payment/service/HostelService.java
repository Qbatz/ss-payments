package com.qbatz.payment.service;

import com.qbatz.payment.dao.HostelPlan;
import com.qbatz.payment.dao.HostelV1;
import com.qbatz.payment.dao.Plans;
import com.qbatz.payment.repositories.HostelRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HostelService {
    @Autowired
    private HostelRepositories hostelRepositories;


    public void activateSubscription(Plans plan, Date startDate, String hostelId, Date endDate, Double paidAmount) {
        HostelV1 hostelV1 = hostelRepositories.getReferenceById(hostelId);
        if (hostelV1 != null) {
            if (hostelV1.getHostelId() != null) {
                HostelPlan hostelPlan = hostelV1.getHostelPlan();
                if (hostelPlan == null) {
                    hostelPlan = new HostelPlan();
                    hostelPlan.setCurrentPlanCode(plan.getPlanCode());
                    hostelPlan.setCurrentPlanName(plan.getPlanName());
                    hostelPlan.setHostel(hostelV1);
                }
                hostelPlan.setCurrentPlanStartsAt(startDate);
                hostelPlan.setCurrentPlanEndsAt(endDate);
                hostelPlan.setCurrentPlanPrice(plan.getPrice());
                hostelPlan.setPaidAmount(paidAmount);
                hostelPlan.setTrial(true);

                hostelV1.setHostelPlan(hostelPlan);

                hostelRepositories.save(hostelV1);
            }
        }
    }
}
