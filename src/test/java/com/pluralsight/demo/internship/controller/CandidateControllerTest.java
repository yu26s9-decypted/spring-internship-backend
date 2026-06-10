package com.pluralsight.demo.internship.controller;

import com.pluralsight.demo.internship.service.CandidateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pluralsight.demo.internship.model.Candidate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void getAllCandidate_shouldReturnNothing() throws Exception {
       when(candidateService.getAllCandidates()).thenReturn(List.of());

       mockMvc.perform(get("/api/candidates")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(0));

    }

    @Test
    void updateCandidate_shouldReturnChanged() throws Exception {
        Candidate candidate1 = new Candidate("Candidate 1", "c1@test.com", "Nursing");
        Candidate candidate2 = new Candidate("Candidate 2", "c2@test.com", "Biology");
        when(candidateService.updateCandidate(anyLong(), any(Candidate.class))).thenReturn(candidate2);

        mockMvc.perform(put("/api/candidates/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                 {
                  "name": "Candidate 2",
                  "email": "c2@test.com",
                  "fieldOfStudy": "Biology"
                 }
                """))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Candidate 2"))
                .andExpect(jsonPath("$.email").value("c2@test.com"))
                .andExpect(jsonPath("$.fieldOfStudy").value("Biology"));

        verify(candidateService, times(1)).updateCandidate(anyLong(), any(Candidate.class));
    }


    @Test
    void createCandidate_shouldPost() throws Exception {
        Candidate candidate1 = new Candidate("Test Subject", "ts@demo.com", "Education");
        when(candidateService.createCandidate(any(Candidate.class))).thenReturn(candidate1);

        mockMvc.perform(post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                  "name": "Test Subject",
                                  "email": "ts@demo.com",
                                  "fieldOfStudy": "Education"
                                  }
                                
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Subject"))
                .andExpect(jsonPath("$.email").value("ts@demo.com"))
                .andExpect(jsonPath("$.fieldOfStudy").value("Education"));

    }

    @Test
    void deleteCandidate_shouldReturnNull() throws Exception {
        Long id = 20L;
        doNothing().when(candidateService).deleteCandidate(id);

        mockMvc.perform(delete("/api/candidates/{id}", id))
                .andExpect(status().isNoContent());

        verify(candidateService, times(1)).deleteCandidate(id);
    }
}