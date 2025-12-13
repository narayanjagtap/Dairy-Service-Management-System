package com.dairyservice.service;

import com.dairyservice.dto.CreateMilkRequestDTO;
import com.dairyservice.dto.CustomerDashboardDTO;
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
public class CustomerService {
    private final MilkRequestRepository milkRequestRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;

    // Create a milk request
    public MilkRequestDTO createRequest(Long customerId, CreateMilkRequestDTO dto) {
        MilkRequest request = new MilkRequest();
        request.setCustomerId(customerId);
        request.setRequestDate(dto.getRequestDate());
        request.setQuantityLiters(dto.getQuantityLiters());
        request.setNote(dto.getNote());
        request.setStatus(MilkRequest.RequestStatus.PENDING);

        MilkRequest saved = milkRequestRepository.save(request);
        return convertToDTO(saved);
    }

    // Get all requests for a customer
    public List<MilkRequestDTO> getMyRequests(Long customerId) {
        List<MilkRequest> requests = milkRequestRepository.findByCustomerId(customerId);
        return requests.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get today's request status for a customer
    public String getTodayRequestStatus(Long customerId) {
        List<MilkRequest> todayRequests = milkRequestRepository
                .findByCustomerIdAndRequestDate(customerId, LocalDate.now());

        if (todayRequests.isEmpty()) {
            return "NO_REQUEST";
        }
        return todayRequests.get(0).getStatus().toString();
    }

    // Get all bills for a customer
    public List<Bill> getMyBills(Long customerId) {
        return billRepository.findByCustomerId(customerId);
    }

    // Get total outstanding amount
    public Double getTotalOutstanding(Long customerId) {
        List<Bill> unpaidBills = billRepository
                .findByCustomerIdAndStatus(customerId, Bill.BillStatus.UNPAID);

        return unpaidBills.stream()
                .mapToDouble(Bill::getAmount)
                .sum();
    }

    // Get customer dashboard
    public CustomerDashboardDTO getDashboard(Long customerId) {
        Optional<User> userOpt = userRepository.findById(customerId);
        if (userOpt.isEmpty()) {
            return null;
        }

        User user = userOpt.get();
        List<MilkRequest> allRequests = milkRequestRepository.findByCustomerId(customerId);
        Long pendingCount = allRequests.stream()
                .filter(r -> r.getStatus() == MilkRequest.RequestStatus.PENDING)
                .count();

        Double outstanding = getTotalOutstanding(customerId);
        String todayStatus = getTodayRequestStatus(customerId);

        CustomerDashboardDTO dashboard = new CustomerDashboardDTO();
        dashboard.setUserId(customerId);
        dashboard.setName(user.getName());
        dashboard.setEmail(user.getEmail());
        dashboard.setTotalRequests((long) allRequests.size());
        dashboard.setPendingRequests(pendingCount);
        dashboard.setTotalOutstandingAmount(outstanding);
        dashboard.setTodayRequestStatus(todayStatus);

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
