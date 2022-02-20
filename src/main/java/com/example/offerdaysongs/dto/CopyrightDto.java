package com.example.offerdaysongs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CopyrightDto {
    long id;
    BigDecimal royalty;
    LocalDate periodStart;
    LocalDate periodEnd;
    long company_id;
    long recording_id;
}
