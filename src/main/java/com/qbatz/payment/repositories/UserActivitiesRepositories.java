package com.qbatz.payment.repositories;

import com.qbatz.payment.dao.UserActivities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivitiesRepositories extends JpaRepository<UserActivities, Long> {
}
