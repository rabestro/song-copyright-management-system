package com.example.offerdaysongs.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UpdateCopyrightRequest {
    long id;

    @NotNull
    @PositiveOrZero
    BigDecimal royalty;
    @NotNull
    LocalDate periodStart;
    @NotNull
    LocalDate periodEnd;
}
