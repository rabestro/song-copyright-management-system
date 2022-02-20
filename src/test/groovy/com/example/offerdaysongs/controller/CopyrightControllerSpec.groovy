package com.example.offerdaysongs.controller

import com.example.offerdaysongs.model.Company
import com.example.offerdaysongs.model.Copyright
import com.example.offerdaysongs.model.Recording
import com.example.offerdaysongs.service.CopyrightService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@WebMvcTest(CopyrightController)
class CopyrightControllerSpec extends Specification {
    @Autowired
    private MockMvc mvc

    def company = new Company(id: 1)
    def recording = new Recording(id: 3)

    @SpringBean
    private CopyrightService copyrightService = Stub()

    def "GET /copyrights/ should return the list of all rights"() {
        given:
        copyrightService.findAll() >> [new Copyright(royalty: 0.99, company: company, recording: recording)]

        expect: "Status is 200 and the response is 'Hello world!'"
        mvc.perform(get("/api/copyrights/"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString.contains '0.99'
    }

}
