package com.gruposalinas.agenda.models;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ContactPhoneId implements Serializable {

    @Column(name = "contact_id")
    private Long contactId;

    @Column(name = "phone_id")
    private Long phoneId;

    public ContactPhoneId() {
    }

    public ContactPhoneId(Long contactId, Long phoneId) {
        this.contactId = contactId;
        this.phoneId = phoneId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactPhoneId)) return false;
        ContactPhoneId that = (ContactPhoneId) o;
        return Objects.equals(contactId, that.contactId)
                && Objects.equals(phoneId, that.phoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId, phoneId);
    }
}