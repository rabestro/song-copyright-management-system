package com.example.offerdaysongs.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "copyright")
public class Copyright {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "royalty")
    BigDecimal royalty;

    @Column(name = "period_start")
    LocalDate periodStart;

    @Column(name = "period_end")
    LocalDate periodEnd;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    Company company;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recording_id")
    Recording recording;

}
