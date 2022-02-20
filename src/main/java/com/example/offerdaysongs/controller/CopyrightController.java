package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.CompanyDto;
import com.example.offerdaysongs.dto.CopyrightDto;
import com.example.offerdaysongs.dto.RecordingDto;
import com.example.offerdaysongs.dto.SingerDto;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public ResponseEntity<Copyright> create(
            @Valid @RequestBody CreateCopyrightRequest createCopyrightRequest
    ) {
//        if (result.hasErrors()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, result.toString());
//        }
        try {
            var copyright = service.create(createCopyrightRequest);
            var location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("{id}")
                    .buildAndExpand(copyright.getId())
                    .toUri();

            return ResponseEntity.created(location).body(copyright);
        } catch (CompanyNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "company not found");
        } catch (RecordingNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "recording not found");
        }
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
