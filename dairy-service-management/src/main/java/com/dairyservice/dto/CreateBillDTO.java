package com.dairyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBillDTO {
    private Long customerId;
    private Double amount; // Final amount entered by admin
    private String description; // Month or description (e.g., "November 2024")
    private LocalDate dueDate;
}
