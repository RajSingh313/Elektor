package com.elektor.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class ElectionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Eelection name is required")
    private String electionName;

    @OneToOne
    @JoinColumn(name = "winner_id")
    private Candidate winner;


}
