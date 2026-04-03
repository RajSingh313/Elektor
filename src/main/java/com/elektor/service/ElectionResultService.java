package com.elektor.service;

import com.elektor.entities.Candidate;
import com.elektor.entities.ElectionResult;
import com.elektor.exception.ResourceNotFoundException;
import com.elektor.repository.CandidateRepository;
import com.elektor.repository.ElectionResultRepository;
import com.elektor.repository.VoterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElectionResultService {


   private CandidateRepository candidateRepository;
   private ElectionResultRepository electionResultRepository;
   private VoterRepository voterRepository;

   public ElectionResultService(CandidateRepository candidateRepository, ElectionResultRepository electionResultRepository, VoterRepository voterRepository) {
       this.candidateRepository = candidateRepository;
       this.electionResultRepository = electionResultRepository;
       this.voterRepository = voterRepository;
   }

   public ElectionResult declareElectionResult(ElectionResult electionResult) {
       Optional<ElectionResult> existingResult = this.electionResultRepository.findByElectionName(electionResult.getElectionName());
    if (existingResult.isPresent()) {
        return existingResult.get();
    }
    if (voterRepository.count()==0){
        throw new IllegalStateException("Can't declare ElectionResult beacuase no vote has been casted");
    }
       List<Candidate> allCnadidates = candidateRepository.findAllByOrderByVoteCountDesc();
    if(allCnadidates.isEmpty()){
        throw new ResourceNotFoundException("No candidate found");

    }
    Candidate candidate = allCnadidates.get(0);
    int totalVotes = 0;
    for (Candidate candidate1 : allCnadidates) {
        totalVotes += candidate1.getVoteCount();

    }
    ElectionResult result = new ElectionResult();
    result.setElectionName(electionResult.getElectionName());
       result.setWinner(candidate);
       result.setTotalVotes(totalVotes);
return electionResultRepository.save(result);

   }
   public List<ElectionResult> getElectionResults() {
       return electionResultRepository.findAll();
   }

}
