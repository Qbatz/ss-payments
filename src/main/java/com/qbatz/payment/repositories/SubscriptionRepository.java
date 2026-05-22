package com.qbatz.payment.repositories;

import com.qbatz.payment.dao.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query(value = "SELECT * FROM subscription WHERE hostel_id=:hostelId order by plan_starts_at DESC LIMIT 1", nativeQuery = true)
    Subscription findLatestSubscription(@Param("hostelId") String hostelId);
}
