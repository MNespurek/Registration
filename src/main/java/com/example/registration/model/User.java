package com.example.registration.model;

import com.example.registration.RegistrationException;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

public class User {

    private Long id;
    private String name;

    private String surname;

    private String personID;

    private UUID uniqueId;


    public User() {
        this.uniqueId = UUID.randomUUID();
    }

    public User(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public User(Long id, String name, String surname, String personId, UUID uniqueId) throws RegistrationException, SQLException, FileNotFoundException {
        this(id, name, surname);
        this.personID = personId;
        this.uniqueId = uniqueId;
    }

    public Long getId() {
        return id;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPersonID() {
        return personID;
    }
}
