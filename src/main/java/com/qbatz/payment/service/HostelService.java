package com.qbatz.payment.service;

import com.qbatz.payment.dao.HostelPlan;
import com.qbatz.payment.dao.HostelV1;
import com.qbatz.payment.dao.KycConfig;
import com.qbatz.payment.dao.Plans;
import com.qbatz.payment.repositories.HostelRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class HostelService {

    @Autowired
    private HostelRepositories hostelRepositories;
    @Autowired
    private PlansService plansService;
    @Autowired
    private KycConfigService kycConfigService;

    public void activateSubscription(Plans plan, Date startDate, String hostelId,
                                     Date endDate, Double paidAmount) {

        if (plan == null){
            return;
        }

        HostelV1 hostel = hostelRepositories.getReferenceById(hostelId);

        List<Plans> freePlans = plansService.getFreePlans();
        Set<String> freePlanCodes = freePlans.stream()
                .map(Plans::getPlanCode)
                .collect(Collectors.toSet());

        KycConfig kycConfig = kycConfigService.getByHostelId(hostelId);

        Date today = new Date();

        HostelPlan hostelPlan = hostel.getHostelPlan();

        boolean isTrial = freePlanCodes.contains(plan.getPlanCode());

        if (hostelPlan == null) {
            hostelPlan = new HostelPlan();
            hostelPlan.setHostel(hostel);
        }
        hostelPlan.setCurrentPlanCode(plan.getPlanCode());
        hostelPlan.setCurrentPlanName(plan.getPlanName());
        hostelPlan.setCurrentPlanStartsAt(startDate);
        hostelPlan.setCurrentPlanEndsAt(endDate);
        hostelPlan.setCurrentPlanPrice(plan.getFinalPrice());
        hostelPlan.setPaidAmount(paidAmount);
        hostelPlan.setTrial(isTrial);
        hostelPlan.setTrialEndingAt(isTrial ? endDate : null);

        hostel.setHostelPlan(hostelPlan);

        int kycPerMonthLimit = plan.getKycPerMonthLimit();

        if (kycConfig == null) {
            kycConfig = new KycConfig();

            kycConfig.setHostelId(hostelId);
            kycConfig.setCanRequest(true);
            kycConfig.setCreatedAt(today);
        } else {
            kycConfig.setUpdatedAt(today);
        }

        kycConfig.setLimitPerMonth(kycPerMonthLimit);

        hostelRepositories.save(hostel);
        kycConfigService.save(kycConfig);
    }
}