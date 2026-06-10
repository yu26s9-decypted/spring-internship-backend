package com.pluralsight.demo.internship.controller;

import com.pluralsight.demo.internship.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pluralsight.demo.internship.model.Candidate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CandidateController.class)
class CandidateControllerTest {
    @Autowired
    private MockMvc mockMvc;
    
    @MockitoBean
    private CandidateService candidateService;
// your tests go here

    @Test
    void getAllCandidates_shouldReturnListOfCandidates() throws Exception {
        Candidate candidate1 = new Candidate("Candidate 1", "c1@test.com", "Nursing");
        Candidate candidate2 = new Candidate("Candidate 2", "c2@test.com", "Biology");

        List<Candidate> candidates = Arrays.asList(candidate1, candidate2);

        when(candidateService.getAllCandidates()).thenReturn(candidates);

        mockMvc.perform(get("/api/candidates")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Candidate 1"))
                .andExpect(jsonPath("$[0].email").value("c1@test.com"))
                .andExpect(jsonPath("$[0].fieldOfStudy").value("Nursing"))
                .andExpect(jsonPath("$.length()").value(2));
    }
}