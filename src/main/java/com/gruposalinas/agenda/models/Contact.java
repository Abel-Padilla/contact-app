package com.gruposalinas.agenda.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @Size(min = 3, max = 100)
    String fullName;

    @Column(nullable = false, unique = true)
    String email;

    @OneToMany( mappedBy = "contact" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();


    @Column(nullable = false)
    Date creationTime;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getFullName() {
        return fullName;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public List<Phone> getPhone() {
        return phones;
    }


    public void setPhone(List<Phone> phone) {
        this.phones = phone;
    }


    public Date getCreationTime() {
        return creationTime;
    }


    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void addPhone(Phone phone){
        phones.add(phone);
        phone.setContact(this);
    }

    public void removePhone(Phone phone){
        phones.remove(phone);
        phone.setContact(null);
    }



    

}
