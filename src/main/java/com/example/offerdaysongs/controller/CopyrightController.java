package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.CopyrightDto;
import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.dto.requests.UpdateCopyrightRequest;
import com.example.offerdaysongs.exception.CompanyNotFoundException;
import com.example.offerdaysongs.exception.RecordingNotFoundException;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.service.CopyrightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateCopyrightRequest updateCopyrightRequest) {
        service.update(updateCopyrightRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/period/{start}/{end}")
    public List<CopyrightDto> findAllByPeriod(
            @PathVariable(name = "start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @PathVariable(name = "end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return service.findAllByPeriod(start, end).stream()
                .map(this::convertToDto)
                .collect(toUnmodifiableList());
    }

    private CopyrightDto convertToDto(Copyright copyright) {
        return CopyrightDto.builder()
                .id(copyright.getId())
                .royalty(copyright.getRoyalty())
                .periodStart(copyright.getStart())
                .periodEnd(copyright.getEnd())
                .company_id(copyright.getCompany().getId())
                .recording_id(copyright.getRecording().getId())
                .build();
    }

}
