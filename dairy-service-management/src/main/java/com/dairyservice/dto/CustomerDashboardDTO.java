package com.dairyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDashboardDTO {
    private Long userId;
    private String name;
    private String email;
    private Long totalRequests;
    private Long pendingRequests;
    private Double totalOutstandingAmount;
    private String todayRequestStatus; // Status of today's request
}
