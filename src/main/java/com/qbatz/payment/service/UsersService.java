package com.qbatz.payment.service;

import com.qbatz.payment.dao.Users;
import com.qbatz.payment.enumm.ActivitySource;
import com.qbatz.payment.enumm.ActivitySourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UserActivitiesService userActivitiesService;

    public void addUserLog(String hostelId, String sourceId, ActivitySource activitySource,
                           ActivitySourceType activitySourceType, Users users) {
        userActivitiesService.addLoginLog(hostelId, null, activitySource.name(), activitySourceType.name(), sourceId, users);
    }
}
