package com.elektor.service;

import com.elektor.entities.Voter;
import com.elektor.exception.DuplicateResourceException;
import com.elektor.exception.ResourceNotFoundException;
import com.elektor.repository.CandidateRepository;
import com.elektor.repository.VoteRepository;
import com.elektor.repository.VoterRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
public class VoterService {

    private VoterRepository voterRepository;
    private CandidateRepository candidateRepository;
    public VoterService(VoterRepository voterRepository, CandidateRepository candidateRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

  public Voter registerVoter(Voter voter){
        if(voterRepository.existsByEmail(voter.getEmail())){
            throw new DuplicateResourceException("Voter with Email id: " + voter.getEmail() + " already exists");
        }
        return voterRepository.save(voter);
    }

    public List<Voter> getAllVoters(){
        return voterRepository.findAll();
    }

    public Voter getVoterById(Long id){
        Voter voter = voterRepository.findById(id).orElse(null);
        if(voter == null){
            throw new ResourceNotFoundException("Voter with id: " + voter.getId() + " not found");
        }
        return voter;
    }
}
