package com.example.registration.model;

import com.example.registration.RegistrationException;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;




public class User {

    UserService userService;
    UserRepository userRepository;
    private Long id;
    private String name;

    private String surname;

    private String personID;

    private UUID uniqueId;


    public User() {
        this.uniqueId = UUID.randomUUID();

    }

    public User(String name, String surname, String personId, UUID uniqueId) throws RegistrationException, SQLException, FileNotFoundException {
        this.name = name;
        this.surname = surname;
        this.personID = personId;
        this.uniqueId = uniqueId;

    }


    public User(Long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;


    }

    public User(Long id, String name, String surname, String personId) throws RegistrationException, SQLException, FileNotFoundException {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.personID = personId;
        this.uniqueId = UUID.randomUUID();
    }



    public User(Long id, String name, String surname, String personId, UUID uniqueId) throws RegistrationException, SQLException, FileNotFoundException {
        this.id = id;
        this.name = name;
        this.surname = surname;
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

    /*public void setPersonId(String personId) throws RegistrationException, SQLException, FileNotFoundException, InvalidParameterException {

        Set<String> setOfIdsToSelectFrom = setOfIdsToSelectFrom();
        System.out.println(setOfIdsToSelectFrom);

    }*/


}
