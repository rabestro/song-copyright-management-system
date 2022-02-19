package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.requests.CreateCopyrightRequest;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.repository.CompanyRepository;
import com.example.offerdaysongs.repository.CopyrightRepository;
import com.example.offerdaysongs.repository.RecordingRepository;
import com.example.offerdaysongs.repository.SingerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CopyrightService {
    private final CopyrightRepository copyrightRepository;
    private final CompanyRepository companyRepository;
    private final RecordingRepository recordingRepository;
    private final SingerRepository singerRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Copyright create(CreateCopyrightRequest request) {

        var copyright = new Copyright();
        copyright.setPeriodStart(request.getPeriodStart());
        copyright.setPeriodEnd(request.getPeriodEnd());
        copyright.setRoyalty(request.getRoyalty());

        Company companyDto = request.getCompany();
        Recording recordingDto = request.getRecording();
        Singer singerDto = request.getRecording().getSinger();

        if (companyDto != null) {
            Company foundCompany = companyRepository.findById(companyDto.getId()).orElseGet(() -> {
                Company temp = new Company();
                temp.setName(companyDto.getName());
                return companyRepository.save(temp);
            });
            copyright.setCompany(foundCompany);
        }
        if (recordingDto != null) {
            Recording foundRecording = recordingRepository.findById(recordingDto.getId()).orElseGet(() -> {
                Recording temp = new Recording();
                temp.setTitle(recordingDto.getTitle());
                temp.setSinger(recordingDto.getSinger());
                temp.setVersion(recordingDto.getVersion());
                temp.setReleaseTime(recordingDto.getReleaseTime());
                return recordingRepository.save(temp);
            });
            copyright.setRecording(foundRecording);
        }
        return copyrightRepository.save(copyright);
    }
}
