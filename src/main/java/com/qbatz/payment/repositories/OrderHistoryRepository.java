package com.qbatz.payment.repositories;

import com.qbatz.payment.dao.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    OrderHistory findFirstByPaymentLinkIdAndOrderStatusOrderByCreatedAtDesc(String paymentLinkId, String orderStatus);

    @Query("""
            SELECT oh FROM OrderHistory oh WHERE oh.userType = 'OWNER'
            """)
    List<OrderHistory> findAllRecordByOwner();

    @Query("""
            SELECT oh FROM OrderHistory oh WHERE oh.hostelId=:hostelId AND oh.orderStatus='PAID'
            ORDER BY oh.createdAt DESC
            """)
    List<OrderHistory> findByHostelIdOrderByCreatedAtDesc(String hostelId);
}
