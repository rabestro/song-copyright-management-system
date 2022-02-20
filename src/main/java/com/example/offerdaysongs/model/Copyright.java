package com.example.offerdaysongs.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "copyright")
public class Copyright {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @PositiveOrZero
    @Column(name = "royalty")
    BigDecimal royalty;

    @NotNull
    @Column(name = "period_start")
    LocalDate periodStart;

    @NotNull
    @Column(name = "period_end")
    LocalDate periodEnd;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    Company company;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recording_id")
    Recording recording;

}
