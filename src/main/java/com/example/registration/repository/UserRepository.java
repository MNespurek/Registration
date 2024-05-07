package com.example.registration.repository;

import com.example.registration.RegistrationException;
import com.example.registration.model.User;
import com.example.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

@Repository
public class UserRepository {


    public ResultSet connectionToDatabaseResultset(String query) throws SQLException, RegistrationException {
        try {
            Connection connection = DriverManager.getConnection(SettingsRepository.DATABASEURL, SettingsRepository.USER, SettingsRepository.PASSWORD);

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;
        }catch (SQLException e) {
            throw new RegistrationException("Nebylo nalezeno spojení s databází!" +e.getLocalizedMessage());

        }
    }

    public PreparedStatement connectionToDatabasePrepareStatement(String query) throws SQLException, RegistrationException {
        try {
            Connection connection = DriverManager.getConnection(SettingsRepository.DATABASEURL, SettingsRepository.USER, SettingsRepository.PASSWORD);
            return connection.prepareStatement(query);
        }catch (SQLException e) {
            throw new RegistrationException("Nebylo nalezeno spojení s databází!" +e.getLocalizedMessage());

        }
    }

    public Set<String> setOfIdsFromDatabase() throws SQLException, RegistrationException {
        try (ResultSet resultset = connectionToDatabaseResultset(SettingsRepository.GETIDSFROMDATABASE)) {

            Set<String> setOfIdFromUsers = new HashSet<>();
            while (resultset.next()) {
                String personIdFromUsers = resultset.getString(SettingsRepository.PERSONID);
                setOfIdFromUsers.add(personIdFromUsers);
            }
            return setOfIdFromUsers;
        }
    }

    public void saveUserToDatabase(User user) throws SQLException, RegistrationException {
        try(PreparedStatement preparedStatement = connectionToDatabasePrepareStatement(SettingsRepository.ADDUSERTODATABASE)) {
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

    public void saveEditUserToDatabase(User user) throws SQLException, RegistrationException {
        SettingsRepository.editUserInDatabase(user);
        System.out.println("Uživatel s id: "+user.getId()+ "byl úspěšně editován v databázi");
    }

    public void deleteUserFromDatabase(String id) throws SQLException, RegistrationException {
        SettingsRepository.deleteUserFromDatabase(id);
        System.out.println("Uživatel byl úspěšně editován v databázi");
    }


    public User getUserFromDatabaseByIdBasicVersion(String id) {
        try(ResultSet resultSet = connectionToDatabaseResultset(SettingsRepository.getUserFromDatabaseByIdBasicVersion(id))) {

                id = String.valueOf(resultSet.getLong(Math.toIntExact(SettingsRepository.ID)));
                String name = resultSet.getString(SettingsRepository.NAME);
                String surname = resultSet.getString(SettingsRepository.SURNAME);
                User user = new User(id, name, surname);

        return user;


        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserFromDatabaseFullVersion(String id) {
        try(ResultSet resultSet = connectionToDatabaseResultset(SettingsRepository.getUserFromDatabaseByIdFullVersion(id))) {

            id = String.valueOf(resultSet.getLong(1));
            String name = resultSet.getString(SettingsRepository.NAME);
            String surname = resultSet.getString(SettingsRepository.SURNAME);
            String personId = resultSet.getString(SettingsRepository.PERSONID);
            UUID uniqueId = UUID.fromString(resultSet.getString(SettingsRepository.UNIQUEID));

            User user = new User(id, name, surname, personId, uniqueId);

            return user;


        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public List<User> getUsersFromDatabaseBasicVersion() {
        try (ResultSet resultSet = connectionToDatabaseResultset(SettingsRepository.getUsersFromDatabaseBasicVersion())) {
            List<User> usersList = new ArrayList<>();
            while (resultSet.next()) {
                String id = String.valueOf(resultSet.getLong(1));
                String name = resultSet.getString(SettingsRepository.NAME);
                String surname = resultSet.getString(SettingsRepository.SURNAME);
                User user = new User(id, name, surname);
                usersList.add(user);


            }
            return usersList;

        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public List<User> getUsersFromDatabaseFullVersion() {

            try (ResultSet resultSet = connectionToDatabaseResultset(SettingsRepository.getUsersFromDatabaseFullVersion())) {
                List<User> usersList = new ArrayList<>();
                while (resultSet.next()) {
                    String id = String.valueOf(resultSet.getLong(1));
                    String name = resultSet.getString(SettingsRepository.NAME);
                    String surname = resultSet.getString(SettingsRepository.SURNAME);
                    User user = new User(id, name, surname);
                    usersList.add(user);


                }
                return usersList;

            } catch (RegistrationException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

