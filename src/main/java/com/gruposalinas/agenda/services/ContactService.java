package com.gruposalinas.agenda.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gruposalinas.agenda.dtos.ContactPatchRequest;
import com.gruposalinas.agenda.dtos.ContactRequest;
import com.gruposalinas.agenda.dtos.ContactResponse;
import com.gruposalinas.agenda.dtos.PhoneRequest;
import com.gruposalinas.agenda.dtos.PhoneResponse;
import com.gruposalinas.agenda.models.Contact;
import com.gruposalinas.agenda.models.Phone;
import com.gruposalinas.agenda.repository.ContactRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContactService {

    // inyeccion de repository

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {

        this.contactRepository = contactRepository;
    }

    public ContactResponse create(ContactRequest contactRequest) {
        Contact contact = new Contact();
        mapRequest(contact, contactRequest);

        Contact savedContact = contactRepository.save(contact);

        return toResponse(savedContact);

    }

    @Transactional(readOnly = true)
    public ContactResponse findById(Long id) {
        Contact contact = getContact(id);
        return toResponse(contact);
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> findAll() {
        return contactRepository.findAll().stream().map(this::toResponse).toList();
    }

    public Contact getContact(Long id) {
        return contactRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "The contact doesn't exist"));
    }

    @Transactional
    public ContactResponse update(Long id, ContactRequest request) {
        Contact contact = getContact(id);

        if (request.fullName() != null) {
            contact.setFullName(request.fullName());
        }

        if (request.email() != null) {
            contact.setEmail(request.email());
        }

        if (request.phones() != null) {
            contact.getPhone().clear();

            for (PhoneRequest phoneRequest : request.phones()) {
                Phone phone = new Phone();
                phone.setNumber(phoneRequest.number());
                phone.setType(phoneRequest.type());

                contact.addPhone(phone);
            }
        }

        Contact savedContact = contactRepository.save(contact);

        return toResponse(savedContact);
    }

    public void delete(Long id) {
        Contact contact = getContact(id);
        contactRepository.delete(contact);
    }

    private void mapRequest(Contact contact, ContactRequest request) {
        contact.setFullName(request.fullName());
        contact.setEmail(request.email());
        contact.setCreationTime(new Date());// colocar fecha actual
        if (request.phones() != null) {
            for (PhoneRequest phoneRequest : request.phones()) {
                Phone phone = new Phone();
                phone.setNumber(phoneRequest.number());
                phone.setType(phoneRequest.type());

                contact.addPhone(phone);
            }
        }
    }

    public ContactResponse toResponse(Contact contact) {
        List<PhoneResponse> phones = contact.getPhone()
                .stream()
                .map(phone -> new PhoneResponse(
                        phone.getId(),
                        phone.getNumber(),
                        phone.getType()))
                .toList();

        ContactResponse contactResponse = new ContactResponse(contact.getId(), contact.getFullName(),
                contact.getEmail(), phones, null);

        return contactResponse;
    }

}
