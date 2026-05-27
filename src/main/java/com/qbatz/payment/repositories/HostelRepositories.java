package com.qbatz.payment.repositories;

import com.qbatz.payment.dao.HostelV1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostelRepositories extends JpaRepository<HostelV1, String> {
}
