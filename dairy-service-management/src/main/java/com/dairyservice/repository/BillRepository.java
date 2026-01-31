package com.dairyservice.repository;

import com.dairyservice.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    // Find bills by customer and status
    List<Bill> findByCustomerIdAndStatus(Long customerId, Bill.BillStatus status);

    // Find all unpaid bills
    List<Bill> findByStatus(Bill.BillStatus status);

    // Find all bills for a customer
    List<Bill> findByCustomerId(Long customerId);

    // Find latest bill for a customer
    Optional<Bill> findFirstByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
