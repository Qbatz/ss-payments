package com.qbatz.payment.service;

import com.qbatz.payment.dao.Plans;
import com.qbatz.payment.enumm.PlanType;
import com.qbatz.payment.repositories.PlansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlansService {

    @Autowired
    private PlansRepository plansRepository;

    public List<Plans> getFreePlans() {
        List<String> planTypes = new ArrayList<>();
        planTypes.add(PlanType.TRIAL.name());
        planTypes.add(PlanType.EXPANDABLE_TRIAL.name());
        List<Plans> listPlans = plansRepository.findByPlanTypeInAndIsActiveTrue(planTypes);
        if (listPlans == null) {
            listPlans = new ArrayList<>();
        }
        return listPlans;
    }
}
