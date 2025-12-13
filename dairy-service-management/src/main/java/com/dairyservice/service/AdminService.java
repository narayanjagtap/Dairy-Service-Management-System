package com.dairyservice.service;

import com.dairyservice.dto.AdminDashboardDTO;
import com.dairyservice.dto.CustomerDTO;
import com.dairyservice.dto.MilkRequestDTO;
import com.dairyservice.entity.Bill;
import com.dairyservice.entity.MilkRequest;
import com.dairyservice.entity.User;
import com.dairyservice.repository.BillRepository;
import com.dairyservice.repository.MilkRequestRepository;
import com.dairyservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final MilkRequestRepository milkRequestRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;

    // Get all requests
    public List<MilkRequestDTO> getAllRequests() {
        List<MilkRequest> requests = milkRequestRepository.findAll();
        return requests.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get today's requests
    public List<MilkRequestDTO> getTodayRequests() {
        List<MilkRequest> requests = milkRequestRepository.findByRequestDate(LocalDate.now());
        return requests.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get pending requests
    public List<MilkRequestDTO> getPendingRequests() {
        List<MilkRequest> requests = milkRequestRepository
                .findByStatus(MilkRequest.RequestStatus.PENDING);
        return requests.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Update request status
    public String updateRequestStatus(Long requestId, String status) {
        Optional<MilkRequest> requestOpt = milkRequestRepository.findById(requestId);
        if (requestOpt.isEmpty()) {
            return "Request not found";
        }

        MilkRequest request = requestOpt.get();
        try {
            request.setStatus(MilkRequest.RequestStatus.valueOf(status));
            milkRequestRepository.save(request);
            return "Request status updated successfully";
        } catch (IllegalArgumentException e) {
            return "Invalid status";
        }
    }

    // Get total accepted liters for a customer (for billing)
    public Double getTotalAcceptedLiters(Long customerId) {
        List<MilkRequest> acceptedRequests = milkRequestRepository
                .findByCustomerIdAndStatus(customerId, MilkRequest.RequestStatus.ACCEPTED);

        return acceptedRequests.stream()
                .mapToDouble(MilkRequest::getQuantityLiters)
                .sum();
    }

    // Get all customers
    public List<CustomerDTO> getAllCustomers() {
        List<User> customers = userRepository.findByRole(User.UserRole.CUSTOMER);
        return customers.stream().map(customer -> {
            Double outstanding = getTotalOutstanding(customer.getId());
            return new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getAddress(),
                    outstanding
            );
        }).collect(Collectors.toList());
    }

    // Get total outstanding amount
    public Double getTotalOutstanding() {
        List<Bill> unpaidBills = billRepository.findByStatus(Bill.BillStatus.UNPAID);
        return unpaidBills.stream()
                .mapToDouble(Bill::getAmount)
                .sum();
    }

    // Get total outstanding for a specific customer
    public Double getTotalOutstanding(Long customerId) {
        List<Bill> unpaidBills = billRepository
                .findByCustomerIdAndStatus(customerId, Bill.BillStatus.UNPAID);
        return unpaidBills.stream()
                .mapToDouble(Bill::getAmount)
                .sum();
    }

    // Get total cash collected (sum of paid bills)
    public Double getTotalCashCollected() {
        List<Bill> paidBills = billRepository.findByStatus(Bill.BillStatus.PAID);
        return paidBills.stream()
                .mapToDouble(Bill::getAmount)
                .sum();
    }

    // Get today's total liters sold
    public Double getTodayTotalLitersSold() {
        List<MilkRequest> todayAccepted = milkRequestRepository
                .findByRequestDateAndStatus(LocalDate.now(), MilkRequest.RequestStatus.ACCEPTED);

        return todayAccepted.stream()
                .mapToDouble(MilkRequest::getQuantityLiters)
                .sum();
    }

    // Get admin dashboard
    public AdminDashboardDTO getDashboard() {
        List<MilkRequest> todayRequests = milkRequestRepository
                .findByRequestDate(LocalDate.now());

        Long pendingCount = todayRequests.stream()
                .filter(r -> r.getStatus() == MilkRequest.RequestStatus.PENDING)
                .count();

        Long totalCustomers = (long) userRepository.findByRole(User.UserRole.CUSTOMER).size();
        Double outstanding = getTotalOutstanding();
        Double collected = getTotalCashCollected();
        Double todayLiters = getTodayTotalLitersSold();

        AdminDashboardDTO dashboard = new AdminDashboardDTO();
        dashboard.setTodayTotalRequests((long) todayRequests.size());
        dashboard.setPendingRequestsCount(pendingCount);
        dashboard.setTotalOutstandingAmount(outstanding);
        dashboard.setTotalCashCollected(collected);
        dashboard.setTotalCustomers(totalCustomers);
        dashboard.setTodayTotalLitersSold(todayLiters);

        return dashboard;
    }

    // Helper method to convert entity to DTO
    private MilkRequestDTO convertToDTO(MilkRequest request) {
        Optional<User> userOpt = userRepository.findById(request.getCustomerId());

        MilkRequestDTO dto = new MilkRequestDTO();
        dto.setId(request.getId());
        dto.setCustomerId(request.getCustomerId());
        dto.setCustomerName(userOpt.map(User::getName).orElse("Unknown"));
        dto.setRequestDate(request.getRequestDate());
        dto.setQuantityLiters(request.getQuantityLiters());
        dto.setNote(request.getNote());
        dto.setStatus(request.getStatus().toString());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());

        return dto;
    }
}
