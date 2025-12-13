package com.dairyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private Long requestId;
    private Double amount;
    private String description; // Month or description
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private String status; // PAID or UNPAID
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
