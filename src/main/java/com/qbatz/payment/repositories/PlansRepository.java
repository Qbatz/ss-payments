package com.qbatz.payment.repositories;

import com.qbatz.payment.dao.Plans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlansRepository extends JpaRepository<Plans, Long> {

    Plans findPlanByPlanCode(String planCode);

    List<Plans> findByPlanTypeInAndIsActiveTrue(List<String> planTypes);
}
