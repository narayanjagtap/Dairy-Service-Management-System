package com.dairyservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMilkRequestDTO {
    private LocalDate requestDate;
    private Double quantityLiters;
    private String note;
}
