package com.gruposalinas.agenda.dtos;

import com.gruposalinas.agenda.models.PhoneType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PhoneRequest(
    @NotBlank 
    @Size (max = 15)
    String number,

    @NotNull
    PhoneType type
) {

}
