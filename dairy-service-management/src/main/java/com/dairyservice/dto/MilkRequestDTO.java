package com.dairyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MilkRequestDTO {
    private Long id;
    private Long customerId;
    private String customerName;
    private LocalDate requestDate;
    private Double quantityLiters;
    private String note;
    private String status; // PENDING, ACCEPTED, REJECTED, COMPLETED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
