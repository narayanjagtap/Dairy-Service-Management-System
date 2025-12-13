package com.dairyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestStatusDTO {
    private String status; // ACCEPTED, REJECTED, or COMPLETED
}
