package com.example.registration.repository;

chanpublic class SettingsRepository {

    public static final String DATABASEURL = "jdbc:mysql://localhost:3306/";

    public static final String USER = "root";
    public static final String PASSWORD = "Heslo123";
    public static final String ADDUSERTODATABASE = "INSERT INTO USERS (name, surname, personId, uniqueId) values(?, ?, ?, ?)";

    public static final String PERSONID = "personId";

    public static final String NAME = "name";

    public static final String SURNAME = "surname";

    public static final String UNIQUEID = "uniqueId";

    public static final String GETIDSFROMDATABASE = "SELECT personId FROM users";

    public static String getUserFromDatabaseByUUID(String uuid) {
        return "SELECT name, surname FROM users WHERE uniqueId = "+uuid+ ";";
    }
    public static String deleteUserFromDatabaseByUUID(String uuid) {
        return "DELETE * FROM users WHERE uniqueId = "+uuid+ ";";
    }









}
