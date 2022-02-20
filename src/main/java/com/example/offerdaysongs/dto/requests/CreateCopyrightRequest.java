package com.example.offerdaysongs.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateCopyrightRequest {
    @NotNull
    @PositiveOrZero
    BigDecimal royalty;
    @NotNull
    LocalDate periodStart;
    @NotNull
    LocalDate periodEnd;

    long company_id;
    long recording_id;
}
