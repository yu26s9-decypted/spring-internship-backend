package com.pluralsight.demo.internship.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pluralsight.demo.internship.model.Candidate;
import com.pluralsight.demo.internship.repository.CandidateRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ControllerServiceTest {
    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateService candidateService;

    @Test
    void getAllCandidates_test(){
        Candidate published1 = new Candidate("Candidate 1", "c1@test.com", "Computer Science");
        Candidate published2 = new Candidate("Candidate 2", "c2@test.com", "Computer Science");

        List<Candidate> allCandidates = Arrays.asList(published1, published2);

        when(candidateRepository.findAll()).thenReturn(allCandidates);

        // Act
        List<Candidate> result = candidateService.getAllCandidates();

        //Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(published1));
        assertTrue(result.contains(published2));
    }


    @Test
    void getAllCanddiate_shouldReturnNone(){
        List<Candidate> allCandidates = List.of();
        when(candidateRepository.findAll()).thenReturn(allCandidates);

        //Act
        List<Candidate> result = candidateService.getAllCandidates();

        //Assert
        assertEquals(0, result.size());
    }

    @Test
    void createCandidate_shouldReturnSavedCandidate(){
        Candidate candidate = new Candidate("Andy", "andy@test.com", "Computer Science");

        when(candidateRepository.save(candidate)).thenReturn(candidate);
        Candidate result = candidateService.createCandidate(candidate);

        assertEquals("Andy", result.getName());
    }

    @Test
    void deleteCandidate_shouldDeleteById(){
        candidateService.deleteCandidate(1L);
        verify(candidateRepository).deleteById(1L);
    }

    @Test
    void updateCandidate_shouldReturnUpdated(){
        Candidate original = new Candidate("Candidate 1", "c1@test.com", "Nursing");
        original.setId(12L);
        Candidate updated = new Candidate("Candidate 12", "candidate1-alt@test.com", "Engineering");

        when(candidateRepository.findById(12L)).thenReturn(Optional.of(original));
        when(candidateRepository.save(any(Candidate.class))).thenReturn(original);

        Candidate result = candidateService.updateCandidate(12L, updated);

        assertEquals("Candidate 12", result.getName());

    }
}
