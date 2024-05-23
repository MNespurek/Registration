package com.example.registration.repository;

import com.example.registration.model.User;

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


    public static String editUserInDatabase(User user) {
        return "UPDATE user SET name = "+user.getName()+ ", surname = "+user.getSurname()+" WHERE ID = "+user.getId()+";";
        //knihovna SQL Query builder
    }

    public static String deleteUserFromDatabase(Long id) {
        return "DELETE * FROM user WHERE ID = "+id+";";
    }

    public static String getUserFromDatabaseByIdBasicVersion(Long id) {
        return "SELECT ID, name, surname FROM user WHERE ID = "+id+ ";";
    }

    public static String getUserFromDatabaseByIdFullVersion(Long id) {
        return "SELECT * FROM user WHERE ID = "+id+ ";";
    }
    public static String getUsersFromDatabaseBasicVersion() {
        return "SELECT ID, name, surname FROM user";
    }

    public static String getUsersFromDatabaseFullVersion() { return "SELECT * FROM user";}

}
