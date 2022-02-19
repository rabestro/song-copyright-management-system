package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.repository.CompanyRepository;
import com.example.offerdaysongs.repository.CopyrightRepository;
import com.example.offerdaysongs.repository.RecordingRepository;
import com.example.offerdaysongs.repository.SingerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.System.Logger.Level.TRACE;

@Service
@RequiredArgsConstructor
public class CopyrightService {
    private static final System.Logger LOGGER = System.getLogger(CopyrightService.class.getName());

    private final CopyrightRepository copyrightRepository;
    private final CompanyRepository companyRepository;
    private final RecordingRepository recordingRepository;
    private final SingerRepository singerRepository;
    private final ModelMapper modelMapper;

    public List<Copyright> findAll() {
        var rights = copyrightRepository.findAll();
        LOGGER.log(TRACE, "found {0} rights", rights.size());
        return rights;
    }

    @Transactional
    public Copyright create(CreateCopyrightRequest request) {
        var copyright = new Copyright();
        copyright.setPeriodStart(request.getPeriodStart());
        copyright.setPeriodEnd(request.getPeriodEnd());
        copyright.setRoyalty(request.getRoyalty());

        var company = companyRepository.findById(request.getCompany_id())
                .orElseThrow(NoSuchElementException::new);
        copyright.setCompany(company);

        var recording = recordingRepository.findById(request.getRecording_id())
                .orElseThrow(NoSuchElementException::new);
        copyright.setRecording(recording);
        return copyrightRepository.save(copyright);
    }

}
