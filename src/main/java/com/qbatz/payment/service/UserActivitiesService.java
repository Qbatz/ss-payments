package com.qbatz.payment.service;

import com.qbatz.payment.config.Authentication;
import com.qbatz.payment.dao.UserActivities;
import com.qbatz.payment.dao.Users;
import com.qbatz.payment.repositories.UserActivitiesRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserActivitiesService {
    @Autowired
    private UserActivitiesRepositories userActivitiesRepositories;
    @Autowired
    private Authentication authentication;

    public void addLoginLog(String hostelId, String date, String source, String operation, String sourceId, Users users) {
        Date loggedAt = new Date();

        UserActivities userActivities = new UserActivities();
        userActivities.setDescription(getActivityDescription(source, operation));
        if (users != null) {
            userActivities.setUserId(users.getUserId());
            userActivities.setParentId(users.getParentId());
        }
        userActivities.setLoggedAt(loggedAt);
        userActivities.setCreatedAt(new Date());
        userActivities.setSource(source);
        userActivities.setSourceId(sourceId);
        userActivities.setActivityType(operation);
        userActivities.setHostelId(hostelId);
        userActivities.setPlatform(authentication.getSource());

        userActivitiesRepositories.save(userActivities);
    }

    private String getActivityDescription(String source, String operation) {
        if ("PAYMENTS".equalsIgnoreCase(source)) {
            if ("CREATE_SESSION".equalsIgnoreCase(operation)) {
                return "Payment session has been created for subscription";
            }
        }
        return "Activity log for " + source + " / " + operation;
    }
}
