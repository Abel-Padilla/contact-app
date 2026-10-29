package com.gruposalinas.agenda.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact_phone")
public class ContactPhone {

    @EmbeddedId
    private ContactPhoneId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("contactId")
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("phoneId")
    @JoinColumn(name = "phone_id", nullable = false)
    private Phone phone;

    @Enumerated(EnumType.STRING)
    @jakarta.persistence.Column(nullable = false, length = 20)
    private PhoneType type;

    public ContactPhone() {
    }

    public ContactPhone(Contact contact, Phone phone, PhoneType type) {
        this.contact = contact;
        this.phone = phone;
        this.type = type;
    }

    public ContactPhoneId getId() {
        return id;
    }

    public void setId(ContactPhoneId id) {
        this.id = id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }
}