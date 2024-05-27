package com.example.registration.repository;

public class SettingsRepository {

    public static final String DATABASEURL = "jdbc:mysql://localhost:3306/users";

    public static final String USER = "root";
    public static final String PASSWORD = "Heslo123";
    public static final String ADDUSERTODATABASE = "INSERT INTO USER (name, surname, personID, uuid) values(?, ?, ?, ?)";
    public static final String ID = "ID";
    public static final String PERSONID = "personID";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String UNIQUEID = "uuid";
    public static final String GETPERSONIDSFROMDATABASE = "SELECT personID FROM user";


    public static String editUserInDatabase() {
        return "UPDATE user SET name = ?, surname = ? WHERE ID = ?;";
    }

    public static String deleteUserFromDatabase() {
        return "DELETE FROM user WHERE ID = ?;";
    }

    public static String getUserFromDatabaseByIdBasicVersion() {
        return "SELECT ID, name, surname FROM user WHERE ID = ?;";
    }

    public static String getUserFromDatabaseByIdFullVersion() {
        return "SELECT * FROM user WHERE ID = ?;";
    }
    public static String getUsersFromDatabaseBasicVersion() {
        return "SELECT ID, name, surname FROM user";
    }

    public static String getUsersFromDatabaseFullVersion() { return "SELECT * FROM user";}
}
