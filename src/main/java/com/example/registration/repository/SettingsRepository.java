package com.example.registration.repository;

import com.example.registration.model.User;

public class SettingsRepository {

    public static final String DATABASEURL = "jdbc:mysql://localhost:3306/";

    public static final String USER = "root";
    public static final String PASSWORD = "Heslo123";
    public static final String ADDUSERTODATABASE = "INSERT INTO USERS (name, surname, personId, uniqueId) values(?, ?, ?, ?)";

    public static final Long ID = 1L;

    public static final String PERSONID = "personId";

    public static final String NAME = "name";

    public static final String SURNAME = "surname";

    public static final String UNIQUEID = "uniqueId";

    public static final String GETIDSFROMDATABASE = "SELECT Id FROM users";


    public static String editUserInDatabase(User user) {
        return "UPDATE users SET name = "+user.getName()+ ", surname = "+user.getSurname()+" WHERE id = "+user.getId()+";";
    }

    public static String deleteUserFromDatabase(String id) {
        return "DELETE * FROM users WHERE id = "+id+";";
    }

    public static String getUserFromDatabaseByIdBasicVersion(String id) {
        return "SELECT name, surname FROM users WHERE uniqueId = "+id+ ";";
    }

    public static String getUserFromDatabaseByIdFullVersion(String id) {
        return "SELECT * FROM users WHERE uniqueId = "+id+ ";";
    }
    public static String getUsersFromDatabaseBasicVersion() {
        return "SELECT id, name, surname FROM users";
    }

    public static String getUsersFromDatabaseFullVersion() { return "SELECT * FROM users;";}



    public static String deleteUserFromDatabaseByUUID(String id) {
        return "DELETE * FROM users WHERE uniqueId = "+id+ ";";
    }









}
