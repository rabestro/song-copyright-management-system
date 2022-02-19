package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.CompanyDto;
import com.example.offerdaysongs.dto.CopyrightDto;
import com.example.offerdaysongs.dto.RecordingDto;
import com.example.offerdaysongs.dto.SingerDto;
import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.service.CopyrightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

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
    public ResponseEntity<Copyright> create(@RequestBody CreateCopyrightRequest createCopyrightRequest) {
        Copyright copyright = service.create(createCopyrightRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(copyright.getId())
                .toUri();

        return ResponseEntity.created(location).body(copyright);
    }

    private CopyrightDto convertToDto(Copyright copyright) {
        var companyDto = new CompanyDto(copyright.getCompany().getId(), copyright.getCompany().getName());
        var recording = copyright.getRecording();
        var singer = copyright.getRecording().getSinger();
        var recordingDto = new RecordingDto(recording.getId(),
                recording.getTitle(),
                recording.getVersion(),
                recording.getReleaseTime(),
                singer != null ? new SingerDto(singer.getId(), singer.getName()) : null);

        return new CopyrightDto(
                copyright.getId(),
                copyright.getRoyalty(),
                copyright.getPeriodStart(),
                copyright.getPeriodEnd(),
                companyDto, recordingDto);
    }

}
