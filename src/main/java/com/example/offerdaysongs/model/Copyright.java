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
    @Column(name = "start")
    LocalDate start;

    @NotNull
    @Column(name = "end")
    LocalDate end;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    Company company;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recording_id")
    Recording recording;

}
