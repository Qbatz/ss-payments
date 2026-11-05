package com.qbatz.payment.repositories;

import com.qbatz.payment.dao.KycHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycHistoryRepository extends JpaRepository<KycHistory, Long> {

    KycHistory findTopByHostelIdOrderByHistoryIdDesc(String hostelId);
}
