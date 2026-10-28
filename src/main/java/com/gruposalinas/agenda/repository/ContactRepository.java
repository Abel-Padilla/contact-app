package com.gruposalinas.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gruposalinas.agenda.models.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}
