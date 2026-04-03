package com.elektor.controller;

import com.elektor.dto.ElectionResultRequestDTO;
import com.elektor.dto.ElectionResultResponseDTO;
import com.elektor.entities.ElectionResult;
import com.elektor.service.ElectionResultService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/result")
public class ElectionResultController {

    private ElectionResultService electionResultService;
    public ElectionResultController(ElectionResultService electionResultService) {
        this.electionResultService = electionResultService;

    }
    @PostMapping("/declare")
    public ResponseEntity<ElectionResultResponseDTO> deleteElectionResult(@RequestBody @Valid ElectionResultRequestDTO electionResultDTO) {
       ElectionResult result= electionResultService.declareElectionResult(electionResultDTO);
       ElectionResultResponseDTO responseDTO = new ElectionResultResponseDTO();
       responseDTO.setElectionName(result.getElectionName());
       responseDTO.setTotalVotes(result.getTotalVotes());
       responseDTO.setWinnerId(result.getWinnerId());
       responseDTO.setWinnerVotes(result.getWinner().getVoteCount());
       return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }



    @GetMapping
    public ResponseEntity<List<ElectionResult>> getAllElectionResults() {
       List<ElectionResult> resutls =  electionResultService.getElectionResults();
       return new ResponseEntity<>(resutls, HttpStatus.OK);

    }
}
