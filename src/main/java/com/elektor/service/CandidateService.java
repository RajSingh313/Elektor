package com.elektor.service;

import com.elektor.entities.Candidate;
import com.elektor.entities.Vote;
import com.elektor.exception.ResourceNotFoundException;
import com.elektor.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    private CandidateRepository candidateRepository;
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    //Create Candidate
    public Candidate addCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    //get All Candidate
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
    //get Single
    public Candidate getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElse(null);
        if (candidate == null) {
            throw new ResourceNotFoundException("Candidate with id " + id + " not found");
        }
        return candidate;
    }

    //Update Candidate

    public Candidate updateCandidate(Long id, Candidate updatedCandidate) {
        Candidate candidate = candidateRepository.getCandidateById(id);
        if(updatedCandidate.getName() != null) {
            candidate.setName(updatedCandidate.getName());
        }
        if(updatedCandidate.getParty() != null) {
            candidate.setParty(updatedCandidate.getParty());
        }
        return candidateRepository.save(candidate);
    }

    //Delete Cnadidate
    public void deleteCandidate(Long id) {
        Candidate candidate = getCandidateById(id);
        List<Vote> votes = candidate.getVotes();
        for (Vote vote : votes) {
            vote.setCandidate(null);
        }
        candidate.getVotes().clear();
        candidateRepository.delete(candidate);
    }














}
