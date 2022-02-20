package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.CopyrightDto;
import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.exception.CompanyNotFoundException;
import com.example.offerdaysongs.exception.RecordingNotFoundException;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.service.CopyrightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@Validated
@RestController
@RequestMapping("api/copyrights")
@RequiredArgsConstructor
public class CopyrightController {
    private final CopyrightService service;

    @GetMapping
    public List<CopyrightDto> findAll() {
        return service.findAll().stream()
                .map(this::convertToDto)
                .collect(toUnmodifiableList());
    }

    @GetMapping("/id={id}")
    public CopyrightDto findById(@PathVariable long id) {
        return service.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "copyright not found"));
    }

    @GetMapping("/company_id={id}")
    public List<CopyrightDto> findAllByCompany(@PathVariable long id) {
        return service.findAllByCompany(id).stream()
                .map(this::convertToDto)
                .collect(toUnmodifiableList());
    }

    @PostMapping("/")
    public CopyrightDto create(@Valid @RequestBody CreateCopyrightRequest createCopyrightRequest) {
        try {
            return convertToDto(service.create(createCopyrightRequest));
        } catch (CompanyNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "company not found");
        } catch (RecordingNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "recording not found");
        }
    }

    private CopyrightDto convertToDto(Copyright copyright) {
        return new CopyrightDto(
                copyright.getId(),
                copyright.getRoyalty(),
                copyright.getPeriodStart(),
                copyright.getPeriodEnd(),
                copyright.getCompany().getId(),
                copyright.getRecording().getId());
    }

}
