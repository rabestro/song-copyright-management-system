package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.dto.requests.UpdateCopyrightRequest;
import com.example.offerdaysongs.exception.CompanyNotFoundException;
import com.example.offerdaysongs.exception.RecordingNotFoundException;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.repository.CompanyRepository;
import com.example.offerdaysongs.repository.CopyrightRepository;
import com.example.offerdaysongs.repository.RecordingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.lang.System.Logger.Level.TRACE;

@Service("copyrightService")
@RequiredArgsConstructor
public class CopyrightServiceImpl implements CopyrightService {
    private static final System.Logger LOGGER = System.getLogger(CopyrightServiceImpl.class.getName());

    private final CopyrightRepository copyrightRepository;
    private final CompanyRepository companyRepository;
    private final RecordingRepository recordingRepository;

    public List<Copyright> findAll() {
        var rights = copyrightRepository.findAll();
        LOGGER.log(TRACE, "found {0} rights", rights.size());
        return rights;
    }

    public List<Copyright> findAllByCompany(long company_id) {
        return companyRepository.findById(company_id)
                .map(copyrightRepository::findAllByCompany)
                .orElse(List.of());
    }

    @Transactional
    public Copyright create(CreateCopyrightRequest request) {
        var copyright = new Copyright();
        copyright.setStart(request.getPeriodStart());
        copyright.setEnd(request.getPeriodEnd());
        copyright.setRoyalty(request.getRoyalty());

        var company = companyRepository
                .findById(request.getCompany_id())
                .orElseThrow(CompanyNotFoundException::new);

        copyright.setCompany(company);

        var recording = recordingRepository
                .findById(request.getRecording_id())
                .orElseThrow(RecordingNotFoundException::new);

        copyright.setRecording(recording);

        return copyrightRepository.save(copyright);
    }

    @Transactional
    public void deleteById(long id) {
        copyrightRepository.deleteById(id);
    }

    @Transactional
    public void update(UpdateCopyrightRequest request) {
        copyrightRepository.update(
                request.getRoyalty(),
                request.getPeriodStart(),
                request.getPeriodEnd(),
                request.getId()
        );
    }

    public Optional<Copyright> findById(long id) {
        return copyrightRepository.findById(id);
    }

    public List<Copyright> findAllByPeriod(@NotNull LocalDate start, @NotNull LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date is later than end date");
        }
        LOGGER.log(TRACE, "find active copyrights for period {0} - {1}", start, end);
        var activeCopyrights = copyrightRepository.findAllByStartBetweenOrEndBetween(start, end, start, end);
        LOGGER.log(TRACE, "found {0} active copyrights", activeCopyrights.size());
        return activeCopyrights;
    }

    public BigDecimal getRecordingPrice(long recording_id, LocalDate date) {
        var recording = recordingRepository
                .findById(recording_id)
                .orElseThrow(RecordingNotFoundException::new);

        return copyrightRepository.findAllByRecordingAndStartBeforeAndEndAfter(recording, date, date).stream()
                .map(Copyright::getRoyalty)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
