package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.CopyrightDto;
import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.exception.CompanyNotFoundException;
import com.example.offerdaysongs.exception.RecordingNotFoundException;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.service.CopyrightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<CopyrightDto>> findAll() {
        var rights = service.findAll().stream()
                .map(this::convertToDto)
                .collect(toUnmodifiableList());

        return ResponseEntity.ok(rights);
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
