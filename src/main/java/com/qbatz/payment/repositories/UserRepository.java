package com.qbatz.payment.repositories;

import com.qbatz.payment.dao.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
    Users findUserByUserId(String userId);
}
