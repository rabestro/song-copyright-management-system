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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.lang.System.Logger.Level.TRACE;
import static java.util.stream.Collectors.toUnmodifiableList;

@Service
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
        copyright.setPeriodStart(request.getPeriodStart());
        copyright.setPeriodEnd(request.getPeriodEnd());
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

    public List<Copyright> findAllByPeriod(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date is later than end date");
        }
        Predicate<Copyright> isActiveDuringPeriod = copyright ->
                copyright.getPeriodStart().isBefore(end) && copyright.getPeriodEnd().isAfter(start);

        return copyrightRepository.findAll().stream()
                .filter(isActiveDuringPeriod)
                .collect(toUnmodifiableList());
    }

}
