package com.qbatz.payment.repositories;

import com.qbatz.payment.dao.Plans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlansRepository extends JpaRepository<Plans, Long> {
    Plans findPlanByPlanCode(String planCode);
}
