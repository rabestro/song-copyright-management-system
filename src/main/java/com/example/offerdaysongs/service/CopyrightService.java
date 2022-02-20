package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.dto.requests.UpdateCopyrightRequest;
import com.example.offerdaysongs.model.Copyright;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CopyrightService {
    Optional<Copyright> findById(long id);

    List<Copyright> findAll();

    List<Copyright> findAllByCompany(long company_id);

    Copyright create(CreateCopyrightRequest request);

    void deleteById(long id);

    void update(UpdateCopyrightRequest request);

    List<Copyright> findAllByPeriod(LocalDate start, LocalDate end);
}
