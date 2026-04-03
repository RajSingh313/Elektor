package com.elektor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ElectionResultRequestDTO {

    @NotNull(message = "Election Result Required")
    private String electionName;
}
