package com.dairyservice.repository;

import com.dairyservice.entity.MilkRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MilkRequestRepository extends JpaRepository<MilkRequest, Long> {

    // Find all requests for a customer
    List<MilkRequest> findByCustomerId(Long customerId);

    // Find requests by customer and status
    List<MilkRequest> findByCustomerIdAndStatus(Long customerId, MilkRequest.RequestStatus status);

    // Find all requests by status
    List<MilkRequest> findByStatus(MilkRequest.RequestStatus status);

    // Find all requests for a specific date
    List<MilkRequest> findByRequestDate(LocalDate requestDate);

    // Find requests by customer and date
    List<MilkRequest> findByCustomerIdAndRequestDate(Long customerId, LocalDate requestDate);

    // Find accepted requests for a customer (for billing calculation)
    List<MilkRequest> findByCustomerIdAndStatus(Long customerId, LocalDate fromDate, LocalDate toDate);

    // Find today's requests
    List<MilkRequest> findByRequestDateAndStatus(LocalDate requestDate, MilkRequest.RequestStatus status);
}
