package com.example.registration.model;

addimport com.example.registration.RegistrationException;
import com.example.registration.config.Settings;

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

    public Set<String> setOfIdsFromDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Heslo123");
        Statement statement = connection.createStatement();
        ResultSet selectPersonIdFromUsers = statement.executeQuery("Select personId from users");
        Set<String> setOfIdFromUsers = new HashSet<>();
        while (selectPersonIdFromUsers.next()) {
            String personIdFromUsers = String.valueOf(selectPersonIdFromUsers.next());
            setOfIdFromUsers.add(personIdFromUsers);
        }
        return setOfIdFromUsers;
    }


    public Set<String> setOfIdsFromFile() throws FileNotFoundException, SQLException, RegistrationException {
        Set<String> setFromDataPersonId = new HashSet<>();

        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(Settings.getPersonIdFile())))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String personIdFromFile = scanner.nextLine();
                setFromDataPersonId.add(personIdFromFile);

            }
            return setFromDataPersonId;


        }catch (FileNotFoundException e) {
            throw new RegistrationException("Soubor "+Settings.getPersonIdFile()+ "nebyl nalezen!" +e);
        }
    }

    public Set<String> setOfIdsToSelectFrom() throws RegistrationException, SQLException, FileNotFoundException {
        Set<String> setOfIdsFromFile = setOfIdsFromFile();
        Set<String> setOfIdsFromDatabase = setOfIdsFromDatabase();
        setOfIdsFromFile.removeAll(setOfIdsFromDatabase);
        Set<String> setOfIdsToSelectFrom = setOfIdsFromFile;
        return setOfIdsToSelectFrom;
    }
}
