package com.example.offerdaysongs.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateCopyrightRequest {
    long id;

    BigDecimal royalty;
    LocalDate periodStart;
    LocalDate periodEnd;
}
