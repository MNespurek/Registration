package com.example.registration.repository;

import com.example.registration.RegistrationException;
import com.example.registration.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@Repository
public class UserRepository {

    public Statement connectionToDatabaseStatement() throws SQLException, RegistrationException {
        try {
            Connection connection = DriverManager.getConnection(Settings.DATABASEURL, Settings.USER, Settings.PASSWORD);
            return connection.createStatement();
        }catch (SQLException e) {
            throw new RegistrationException("Nebylo nalezeno spojení s databází!" +e.getLocalizedMessage());

        }
    }

    public PreparedStatement connectionToDatabasePrepareStatement(String add) throws SQLException, RegistrationException {
        try {
            Connection connection = DriverManager.getConnection(Settings.DATABASEURL, Settings.USER, Settings.PASSWORD);
            return connection.prepareStatement(add);
        }catch (SQLException e) {
            throw new RegistrationException("Nebylo nalezeno spojení s databází!" +e.getLocalizedMessage());

        }
    }


    public Set<String> setOfIdsFromDatabase() throws SQLException, RegistrationException {
        Statement statement = connectionToDatabaseStatement();
        ResultSet selectPersonIdFromUsers = statement.executeQuery(Settings.GETIDSFROMDATABASE);
        Set<String> setOfIdFromUsers = new HashSet<>();
        while (selectPersonIdFromUsers.next()) {
            String personIdFromUsers = String.valueOf(selectPersonIdFromUsers.next());
            setOfIdFromUsers.add(personIdFromUsers);
        }
        return setOfIdFromUsers;
    }

    public void saveUserToDatabase(User user) throws SQLException, RegistrationException {
        try(PreparedStatement preparedStatement = connectionToDatabasePrepareStatement(Settings.DATABASEURL)) {
            ;
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPersonId());
            preparedStatement.setString(4, user.getUniqueId().toString());
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            throw new RegistrationException("Nebylo nalezeno spojení s databází!"+e.getLocalizedMessage());
        }
    }

    public User getUserFromDatabaseBasicVersion(String personId) {


    }

    public User getUserFromDatabaseFullVersion(String personId) {


    }

}


