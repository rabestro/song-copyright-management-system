package com.example.offerdaysongs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyrightDto {
    long id;
    BigDecimal royalty;
    LocalDate periodStart;
    LocalDate periodEnd;
    CompanyDto company;
    RecordingDto recording;
}
