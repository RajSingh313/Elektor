package com.elektor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class VoteRequestDTO {
    @NotNull(message = "Voter id is required")
    Long voterId;

    @NotNull(message = "Candidate id is reuired")
    Long candidateId;


}
