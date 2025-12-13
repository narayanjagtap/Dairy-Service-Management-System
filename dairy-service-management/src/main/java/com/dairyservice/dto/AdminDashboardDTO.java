package com.dairyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {
    private Long todayTotalRequests;
    private Long pendingRequestsCount;
    private Double totalOutstandingAmount;
    private Double totalCashCollected; // Sum of PAID bills
    private Long totalCustomers;
    private Double todayTotalLitersSold;
}
