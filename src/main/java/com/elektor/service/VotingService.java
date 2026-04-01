package com.elektor.service;

import com.elektor.entities.Candidate;
import com.elektor.entities.Vote;
import com.elektor.entities.Voter;
import com.elektor.exception.ResourceNotFoundException;
import com.elektor.exception.VoteNotAllowedException;
import com.elektor.repository.CandidateRepository;
import com.elektor.repository.VoteRepository;
import com.elektor.repository.VoterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingService {

    private VoteRepository voteRepository;
    private CandidateRepository candidateRepository; // using to increase vote count
    private VoterRepository voterRepository; // using to make hasVoted = true

    public VotingService(VoteRepository voteRepository, CandidateRepository candidateRepository, VoterRepository voterRepository) {
        this.voteRepository = voteRepository;
        this.candidateRepository = candidateRepository;
        this.voterRepository = voterRepository;
    }

    @Transactional
    public Vote castVote(Long voterId, Long candidateId) {
        if(!voterRepository.existsById(voterId)) {
            throw new ResourceNotFoundException("Vote does not exist With Id: " + voterId);
        }
        if(!candidateRepository.existsById(candidateId)) {
            throw new ResourceNotFoundException("Candidate does not exist With Id: " + candidateId);
        }

        Voter voter = voterRepository.findById(voterId).get();
        if (voter.isHasVoted()){
            throw new VoteNotAllowedException("Voter ID: " + voterId + " is already voted");
        }
        Candidate candidate = candidateRepository.findById(candidateId).get();
        Vote vote = new Vote();
        vote.setVoter(voter);
        vote.setCandidate(candidate);
         voteRepository.save(vote);

         candidate.setVoteCount(candidate.getVoteCount() + 1);
         candidateRepository.save(candidate);
         voter.setHasVoted(true);
         voterRepository.save(voter);


  return vote;

    }

    public List<Vote> getAllVoters() {
        return voteRepository.findAll();
    }
}
