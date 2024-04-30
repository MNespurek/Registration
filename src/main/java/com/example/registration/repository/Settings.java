package com.example.registration.repository;

public class Settings {

    public static final String DATABASEURL = "jdbc:mysql://localhost:3306/";

    public static final String USER = "root";
    public static final String PASSWORD = "Heslo123";
    public static final String ADDUSERTODATABASE = "INSERT INTO USERS (name, surname, personId, uniqueId) values(?, ?, ?, ?)";

    public static final String GETIDSFROMDATABASE = "Select personId from users";









}
