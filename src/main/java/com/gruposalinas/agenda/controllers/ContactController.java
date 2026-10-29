package com.gruposalinas.agenda.controllers;

import com.gruposalinas.agenda.repository.ContactRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gruposalinas.agenda.dtos.ContactPatchRequest;
import com.gruposalinas.agenda.dtos.ContactRequest;
import com.gruposalinas.agenda.dtos.ContactResponse;
import com.gruposalinas.agenda.models.Contact;
import com.gruposalinas.agenda.services.ContactService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService, ContactRepository contactRepository) {
        // inyect dependencies
        this.contactService = contactService;

    }

    @PostMapping("")
    public ContactResponse create(@RequestBody ContactRequest contactRequest) {
        return contactService.create(contactRequest);
    }

    @GetMapping("/{id}")
    public ContactResponse findOne(@PathVariable Long id) {
        return contactService.findById(id);
    }

    @GetMapping("")
    public List<ContactResponse> getMethodName() {
        return contactService.findAll();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContactResponse> patch(
            @PathVariable Long id,
            @RequestBody ContactRequest request) {
        return ResponseEntity.ok(contactService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        contactService.delete(id);
    }

}
