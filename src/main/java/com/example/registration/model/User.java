package com.example.registration.model;

import com.example.registration.RegistrationException;
import com.example.registration.config.Settings;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.InvalidParameterException;
import java.sql.*;
import java.util.*;


public class User {
    private String name;

    private String surname;

    private String personId;

    private UUID uniqueId;

    public User(String name, String surname, String personId) throws RegistrationException, SQLException, FileNotFoundException {
        this.name = name;
        this.surname = surname;
        setPersonId(personId);
        this.uniqueId = UUID.randomUUID();
    }
    @Autowired
    UserService userService;
    UserRepository userRepository;


    public UUID getUniqueId() {
        return uniqueId;
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) throws RegistrationException, SQLException, FileNotFoundException, InvalidParameterException {
        Set<String> setOfIdsToSelectFrom = setOfIdsToSelectFrom();
        if (setOfIdsToSelectFrom.contains(personId)) {
            this.personId = personId;
        }
        else System.out.println("Zadaná hodnota "+personId+ " není definována v souboru možných hodnot " +Settings.getPersonIdFile()+ ".");
    }

    public Set<String> setOfIdsToSelectFrom() throws RegistrationException, SQLException, FileNotFoundException {
        Set<String> setOfIdsFromFile = userService.setOfIdsFromFile();
        Set<String> setOfIdsFromDatabase = userRepository.setOfIdsFromDatabase();
        setOfIdsFromFile.removeAll(setOfIdsFromDatabase);
        Set<String> setOfIdsToSelectFrom = setOfIdsFromFile;
        return setOfIdsToSelectFrom;
    }
}
