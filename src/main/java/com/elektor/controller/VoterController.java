package com.elektor.controller;

import com.elektor.entities.Voter;
import com.elektor.service.VoterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/voters")
@CrossOrigin
public class VoterController {

    private VoterService voterService;
    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    //Create Voter
    @PostMapping("/register")
    public ResponseEntity<Voter> registerVoter( @Valid @RequestBody Voter voter) {
        Voter savedVoter = voterService.registerVoter(voter);
        return new ResponseEntity<>(savedVoter, HttpStatus.CREATED);
    }

    //Get Single Voter

    @GetMapping("/{id}")
    public ResponseEntity<Voter> getVoterById(@PathVariable Long id){
        Voter voter = voterService.getVoterById(id);
        return new ResponseEntity<>(voter,HttpStatus.OK);

    }

    //Get All
    @GetMapping
    public ResponseEntity<List<Voter>> getAllVoters(){
        List<Voter> allVoters = voterService.getAllVoters();
        return new ResponseEntity<>(allVoters, HttpStatus.OK);
    }

    //update voter
    @PutMapping("/update/{id}")
    public ResponseEntity<Voter> updateVoter(@PathVariable Long id, @Valid @RequestBody Voter voter){
        Voter updatedVoter = voterService.updateVoter(id, voter);
        return new ResponseEntity<>(updatedVoter, HttpStatus.OK);
    }

    //delete Router
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVoter(@PathVariable Long id){
        voterService.deleteVoter(id);
        return new ResponseEntity<>("Voter deleted successfully with id : "  + id , HttpStatus.OK);

    }


}
