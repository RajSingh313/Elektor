package com.elektor.controller;

import com.elektor.dto.VoteRequestDTO;
import com.elektor.dto.VoteResponseDTO;
import com.elektor.entities.Vote;
import com.elektor.entities.Voter;
import com.elektor.service.VotingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voting/api")
public class VotingController {

    private VotingService votingService;
    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @PostMapping("/votecast")
    public ResponseEntity<VoteResponseDTO> castVote(@RequestBody @Valid VoteRequestDTO voteRequestDTO) {
        Vote vote = votingService.castVote(voteRequestDTO.getVoterId(), voteRequestDTO.getCandidateId());
        VoteResponseDTO voteResponseDTO = new VoteResponseDTO("vote casted successfully", true, vote.getVoterId(),vote.getCandidateId());
        return new ResponseEntity<>(voteResponseDTO, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Vote>> getVotes() {
        List<Vote> allVoters = votingService.getAllVoters();
        return new ResponseEntity<>(allVoters, HttpStatus.OK);

    }

}
