package com.example.offerdaysongs.repository;

import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Copyright;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CopyrightRepository extends JpaRepository<Copyright, Long>, JpaSpecificationExecutor<Copyright> {
    @Modifying
    @Query("update Copyright c set c.royalty = ?1, c.periodStart = ?2, c.periodEnd = ?3 where c.id = ?4")
    void update(BigDecimal royalty, LocalDate periodStart, LocalDate periodEnd, long id);

    List<Copyright> findAllByCompany(Company company);
}
