package com.qbatz.payment.service;

import com.qbatz.payment.dao.Plans;
import com.qbatz.payment.dao.Subscription;
import com.qbatz.payment.dao.OrderHistory;
import com.qbatz.payment.repositories.PlansRepository;
import com.qbatz.payment.repositories.SubscriptionRepository;
import com.qbatz.payment.Utils;
import com.qbatz.payment.enumm.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private HostelService hostelService;
    @Autowired
    private PlansRepository plansRepository;

    public void subscribe(OrderHistory oh) {
        Plans plan = plansRepository.findPlanByPlanCode(oh.getPlanCode());
        if (plan == null) {
            System.out.println("Plan not found for code: " + oh.getPlanCode());
            return;
        }

        Subscription runningSubscription = subscriptionRepository.findLatestSubscription(oh.getHostelId());
        Date startDate;
        boolean shouldActivateImmediately = false;

        if (runningSubscription != null && runningSubscription.getPlanEndsAt() != null) {
            startDate = Utils.addDaysToDate(runningSubscription.getPlanEndsAt(), 1);
            if (Utils.compareWithTwoDates(runningSubscription.getPlanEndsAt(), new Date()) < 0) {
                startDate = new Date();
                shouldActivateImmediately = true;
            }
        } else {
            startDate = new Date();
            shouldActivateImmediately = true;
        }

        Date endDate = Utils.addDaysToDate(startDate, plan.getDuration().intValue());

        double discountPercentage = 0.0;
        if (oh.getPlanAmount() != null && oh.getPlanAmount() > 0) {
            discountPercentage = (oh.getDiscountAmount() / oh.getPlanAmount()) * 100;
        }

        Subscription subscription = new Subscription();
        subscription.setOrderId(oh.getHistoryId());
        subscription.setSubscriptionNumber("ABCD1234");
        subscription.setHostelId(oh.getHostelId());
        subscription.setPlanCode(oh.getPlanCode());
        subscription.setPlanName(plan.getPlanName());
        subscription.setPlanStartsAt(startDate);
        subscription.setPaidAmount(oh.getTotalAmount());
        subscription.setPlanAmount(oh.getPlanAmount());
        subscription.setDiscount(discountPercentage);
        subscription.setDiscountAmount(oh.getDiscountAmount());
        subscription.setCreatedBy(oh.getCreatedBy());
        subscription.setCreatedByUserType(UserType.OWNER.name());
        subscription.setCreatedAt(new Date());
        subscription.setPlanEndsAt(endDate);
        subscription.setNextBillingAt(endDate);
        subscription.setActivatedAt(startDate);
        subscription.setIsActive(true);

        Subscription sub = subscriptionRepository.save(subscription);
        if (shouldActivateImmediately) {
            hostelService.activateSubscription(plan, startDate, oh.getHostelId(), endDate, oh.getTotalAmount());
        }

    }
}
