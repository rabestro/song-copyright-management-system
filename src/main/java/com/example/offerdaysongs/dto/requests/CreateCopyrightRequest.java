package com.example.offerdaysongs.dto.requests;

import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Recording;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateCopyrightRequest {
    BigDecimal royalty;
    LocalDate periodStart;
    LocalDate periodEnd;

    Company company;
    Recording recording;
}
