package com.elektor.service;

import com.elektor.entities.Candidate;
import com.elektor.entities.Vote;
import com.elektor.entities.Voter;
import com.elektor.exception.DuplicateResourceException;
import com.elektor.exception.ResourceNotFoundException;
import com.elektor.repository.CandidateRepository;
import com.elektor.repository.VoteRepository;
import com.elektor.repository.VoterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Service
public class VoterService {
     //Required Object
    private VoterRepository voterRepository;
    private CandidateRepository candidateRepository;
    public VoterService(VoterRepository voterRepository, CandidateRepository candidateRepository) {
        this.voterRepository = voterRepository;
        this.candidateRepository = candidateRepository;
    }

    //Register Voter
  public Voter registerVoter(Voter voter){
        if(voterRepository.existsByEmail(voter.getEmail())){
            throw new DuplicateResourceException("Voter with Email id: " + voter.getEmail() + " already exists");
        }
        return voterRepository.save(voter);
    }

    //Get All Voters
    public List<Voter> getAllVoters(){
        return voterRepository.findAll();
    }

    //Get Single Voter by id
    public Voter getVoterById(Long id){
        Voter voter = voterRepository.findById(id).orElse(null);
        if(voter == null){
            throw new ResourceNotFoundException("Voter with id: " + id + " not found");
        }
        return voter;
    }

    //Update Voter
    public Voter updateVoter(Long id, Voter updatedVoter){
        Voter voter = voterRepository.findById(id).orElse(null);
        if(voter == null){
            throw new ResourceNotFoundException("Voter with id: " + id + " not found");
        }

        if (updatedVoter.getName()!=null){
        voter.setName(updatedVoter.getName());
        }
        if (updatedVoter.getEmail()!=null) {
            voter.setEmail(updatedVoter.getEmail());
        }
        return voterRepository.save(voter);

    }

    //Delete Voter
    @Transactional
    public void deleteVoter(Long id){
        Voter voter = voterRepository.findById(id).orElse(null);
        if(voter == null){
            throw new ResourceNotFoundException("Can not delete with id: " + id + " as voter not found");
        }
        Vote vote = voter.getVote();
        if(vote != null){
            Candidate candidate = vote.getCandidate();
            candidate.setVoteCount(candidate.getVoteCount() - 1);
            candidateRepository.save(candidate);
        }
        voterRepository.delete(voter);
    }
}
