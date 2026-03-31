package com.elektor.controller;

import com.elektor.entities.Candidate;
import com.elektor.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {
    
    private CandidateService candidateService;
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }
    
    //Create
    @PostMapping("/add")
    public ResponseEntity<Candidate> addCandidate(@Valid @RequestBody Candidate candidate) {
        Candidate savedCandidate = candidateService.addCandidate(candidate);
        return new ResponseEntity<>(savedCandidate, HttpStatus.CREATED);

    }

    //Get All
@GetMapping
    public ResponseEntity<List<Candidate>> getCandidates() {
        List<Candidate> allCandidates = candidateService.getAllCandidates();
        return new ResponseEntity<>(allCandidates, HttpStatus.OK);
    }
    
    //Get Single
    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable  Long id) {
        Candidate candidate = candidateService.getCandidateById(id);
        return new ResponseEntity<>(candidate, HttpStatus.OK);
    }
    
    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@Valid @PathVariable Long id,  @RequestBody Candidate candidate) {
        Candidate updatedCandidate = candidateService.updateCandidate(id, candidate);
        return new ResponseEntity<>(updatedCandidate, HttpStatus.OK);
    }

    //Delete
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCandidateById(@PathVariable  Long id) {
        candidateService.getCandidateById(id);
        return new ResponseEntity<>("Candidate with id "+ id +  "Deleted Successfully", HttpStatus.OK);

    }
}
