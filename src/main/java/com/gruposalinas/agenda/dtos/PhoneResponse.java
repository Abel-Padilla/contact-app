package com.gruposalinas.agenda.dtos;

import com.gruposalinas.agenda.models.PhoneType;

public record PhoneResponse(
    Long id,
    String number,
    PhoneType type
) {

}
