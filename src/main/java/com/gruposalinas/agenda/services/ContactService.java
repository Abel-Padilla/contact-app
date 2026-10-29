
package com.gruposalinas.agenda.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gruposalinas.agenda.dtos.ContactRequest;
import com.gruposalinas.agenda.dtos.ContactResponse;
import com.gruposalinas.agenda.dtos.PhoneRequest;
import com.gruposalinas.agenda.dtos.PhoneResponse;
import com.gruposalinas.agenda.models.Contact;
import com.gruposalinas.agenda.models.ContactPhone;
import com.gruposalinas.agenda.models.Phone;
import com.gruposalinas.agenda.repository.ContactRepository;
import com.gruposalinas.agenda.repository.PhoneRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final PhoneRepository phoneRepository;

    public ContactService(
            ContactRepository contactRepository,
            PhoneRepository phoneRepository) {
        this.contactRepository = contactRepository;
        this.phoneRepository = phoneRepository;
    }

    @Transactional
    public ContactResponse create(ContactRequest request) {
        Contact contact = new Contact();
        contact.setFullName(request.fullName());
        contact.setEmail(request.email());

        if (request.phones() != null) {
            for (PhoneRequest phoneRequest : request.phones()) {
                addPhone(contact, phoneRequest);
            }
        }

        Contact savedContact = contactRepository.save(contact);
        return toResponse(savedContact);
    }

    @Transactional(readOnly = true)
    public ContactResponse findById(Long id) {
        Contact contact = getActiveContact(id);
        return toResponse(contact);
    }

    @Transactional(readOnly = true)
    public List<ContactResponse> findAll() {
        return contactRepository.findAllByActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Contact getActiveContact(Long id) {
        return contactRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Contact not found: " + id));
    }

    @Transactional
    public ContactResponse update(Long id, ContactRequest request) {
        Contact contact = getActiveContact(id);

        if (request.fullName() != null) {
            contact.setFullName(request.fullName());
        }

        if (request.email() != null) {
            contact.setEmail(request.email());
        }

        if (request.phones() != null) {
            contact.getPhones().clear();

            for (PhoneRequest phoneRequest : request.phones()) {
                addPhone(contact, phoneRequest);
            }
        }

        Contact savedContact = contactRepository.save(contact);
        return toResponse(savedContact);
    }

    @Transactional
    public void delete(Long id) {
        Contact contact = getActiveContact(id);
        contact.setActive(false);
        contactRepository.save(contact);
    }

    private void addPhone(Contact contact, PhoneRequest request) {
        Phone phone = phoneRepository.findByNumber(request.number())
                .orElseGet(() -> {
                    Phone newPhone = new Phone();
                    newPhone.setNumber(request.number());
                    return phoneRepository.save(newPhone);
                });

        boolean alreadyAssociated = contact.getPhones().stream()
                .anyMatch(cp ->
                        cp.getPhone().getId().equals(phone.getId()));

        if (alreadyAssociated) {
            throw new IllegalArgumentException(
                    "The phone number is already associated with this contact: "
                            + request.number());
        }

        ContactPhone contactPhone = new ContactPhone();
        contactPhone.setContact(contact);
        contactPhone.setPhone(phone);
        contactPhone.setType(request.type());

        contact.addPhone(contactPhone);
    }

    public ContactResponse toResponse(Contact contact) {
        List<PhoneResponse> phones = contact.getPhones()
                .stream()
                .map(cp -> new PhoneResponse(
                        cp.getPhone().getId(),
                        cp.getPhone().getNumber(),
                        cp.getType()))
                .toList();

        return new ContactResponse(
                contact.getId(),
                contact.getFullName(),
                contact.getEmail(),
                phones,
                contact.getCreationTime());
    }
}
