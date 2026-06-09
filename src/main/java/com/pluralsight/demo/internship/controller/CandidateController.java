package com.pluralsight.demo.internship.controller;

import com.pluralsight.demo.internship.model.Candidate;
import com.pluralsight.demo.internship.service.CandidateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.swing.text.html.parser.Entity;

@RestController
@RequestMapping("/api/candidates")
@CrossOrigin(origins = "*")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates(@RequestParam(required = false) String fieldofstudy) {
        List<Candidate> results;
        if(fieldofstudy == null){
            results = candidateService.getAllCandidates();
        } else {
            results = candidateService.getAllCandidatesByFieldOfStudy(fieldofstudy);
        }

        return ResponseEntity.ok(results);
    }

    @GetMapping("/search/name/{name}")
    // ? is wildcard.
    public ResponseEntity<?> searchByName(@PathVariable String name) {
        List<Candidate> results = candidateService.getByCandidateName(name);
        
        if(results.isEmpty()){
            return ResponseEntity.ok("There is no candidate with the name" + name);
        }
        return ResponseEntity.ok(results);
    }

     @GetMapping("/search/email/{email}")
    public ResponseEntity<Candidate> searchByEmail(@PathVariable String email) {
        System.out.println("Email " + email);
        Candidate results = candidateService.getByCandidateEmail(email).orElse(null);
        System.out.println("Result:"  + results);

    
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        Candidate candidate = candidateService.getCandidateById(id);
        return ResponseEntity.ok(candidate);
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        // Same flaw: returns 200 instead of 201
        Candidate created = candidateService.createCandidate(candidate);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(
            @PathVariable Long id,
            @RequestBody Candidate candidate) {
        Candidate updated = candidateService.updateCandidate(id, candidate);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }
}
