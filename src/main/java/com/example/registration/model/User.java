package com.example.registration.model;

import com.example.registration.config.Settings;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.*;

public class User {
    private String name;

    private String surname;

    private String personId;

    public User(String name, String surname, String personId) {
        this.name = name;
        this.surname = surname;
        setPersonId(personId);
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

    public Set<String> listOfIdsFromDatabase() throws SQLException {
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

    public void setPersonId(String personIdFromPostman) throws FileNotFoundException, SQLException {
        Set<String> setOfIdFromDatabase = listOfIdsFromDatabase();

        for(String personIdFromDatabase : listOfIdFromDatabase) {
            if (personIdFromPostman.equalsIgnoreCase(personIdFromDatabase)) {
                throw new SQLException("Vámi zadaná hodnota Id " +personIdFromPostman+" je již v databázi uložena! Zadejte prosím jinou hodnotu.");
            }
            else ()
        }
        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(Settings.getPersonIdFile())))) {
            scanner.nextLine();
            String output = "";
            while (scanner.hasNextLine()) {
                String personIdFromFile = scanner.nextLine();
                if (personIdFromFile.equals(personIdFromPostman)) {

                }
            }

        }

    }
}
