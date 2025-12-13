package com.dairyservice.service;

import com.dairyservice.dto.BillDTO;
import com.dairyservice.dto.CreateBillDTO;
import com.dairyservice.entity.Bill;
import com.dairyservice.entity.User;
import com.dairyservice.repository.BillRepository;
import com.dairyservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final UserRepository userRepository;

    // Create a bill (Admin enters final amount)
    public BillDTO createBill(CreateBillDTO dto) {
        Bill bill = new Bill();
        bill.setCustomerId(dto.getCustomerId());
        bill.setAmount(dto.getAmount()); // Admin enters this manually
        bill.setDescription(dto.getDescription());
        bill.setDueDate(dto.getDueDate());
        bill.setStatus(Bill.BillStatus.UNPAID);

        Bill saved = billRepository.save(bill);
        return convertToDTO(saved);
    }

    // Get all bills for a customer
    public List<BillDTO> getCustomerBills(Long customerId) {
        List<Bill> bills = billRepository.findByCustomerId(customerId);
        return bills.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get all bills
    public List<BillDTO> getAllBills() {
        List<Bill> bills = billRepository.findAll();
        return bills.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Mark bill as paid
    public String markBillAsPaid(Long billId) {
        Optional<Bill> billOpt = billRepository.findById(billId);
        if (billOpt.isEmpty()) {
            return "Bill not found";
        }

        Bill bill = billOpt.get();
        bill.setStatus(Bill.BillStatus.PAID);
        bill.setPaymentDate(LocalDate.now());
        billRepository.save(bill);
        return "Bill marked as paid";
    }

    // Helper method to convert entity to DTO
    private BillDTO convertToDTO(Bill bill) {
        Optional<User> userOpt = userRepository.findById(bill.getCustomerId());

        BillDTO dto = new BillDTO();
        dto.setId(bill.getId());
        dto.setCustomerId(bill.getCustomerId());
        dto.setCustomerName(userOpt.map(User::getName).orElse("Unknown"));
        dto.setRequestId(bill.getRequestId());
        dto.setAmount(bill.getAmount());
        dto.setDescription(bill.getDescription());
        dto.setDueDate(bill.getDueDate());
        dto.setPaymentDate(bill.getPaymentDate());
        dto.setStatus(bill.getStatus().toString());
        dto.setCreatedAt(bill.getCreatedAt());
        dto.setUpdatedAt(bill.getUpdatedAt());

        return dto;
    }
}
