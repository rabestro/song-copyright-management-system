package com.example.offerdaysongs.service

import com.example.offerdaysongs.model.Copyright
import com.example.offerdaysongs.repository.CompanyRepository
import com.example.offerdaysongs.repository.CopyrightRepository
import com.example.offerdaysongs.repository.RecordingRepository
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title("Copyright Service")
class CopyrightServiceImplSpec extends Specification {

    def copyrightRepository = Mock CopyrightRepository
    def recordingRepository = Mock RecordingRepository
    def companyRepository = Mock CompanyRepository

    @Subject
    def copyrightService = new CopyrightServiceImpl(copyrightRepository, companyRepository, recordingRepository)

    def 'should find all copyrights'() {
        when: 'we request for all copyrights'
        copyrightService.findAll()

        then: 'the service interacts with the repository'
        1 * copyrightRepository.findAll() >> []

        and: 'no more interactions'
        0 * _._
    }

    def 'should find copyright by id'() {
        when: 'we request a copyright by existing id'
        def result = copyrightService.findById(id)

        then: 'the service interacts with the repository'
        1 * copyrightRepository.findById(id) >> Optional.ofNullable(copyright)

        and: 'the result is present'
        with(result) {
            isPresent()
            get() == copyright
        }

        where:
        id | copyright
        1  | new Copyright(id: id, royalty: 0.99)
        2  | new Copyright(id: id, royalty: 1.99)
    }

    def 'should find all active copyrights for a period'() {

    }
}
