package com.gruposalinas.agenda.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gruposalinas.agenda.models.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByIdAndActiveTrue(Long id);

    List<Contact> findAllByActiveTrue();
}
