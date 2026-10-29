package com.gruposalinas.agenda.dtos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record ContactPatchRequest(

        @Size (min = 2, max = 100)
        String fullName,

        @Email 
        @Size(max = 150)
        String email,

        @Valid 
        List<PhoneRequest> phones

) {}
