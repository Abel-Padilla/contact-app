package com.gruposalinas.agenda.dtos;

import java.util.Date;
import java.util.List;

public record ContactResponse(
        Long id,
        String fullName,
        String email,
        List<PhoneResponse> phones,
        Date creationTime
) {
}