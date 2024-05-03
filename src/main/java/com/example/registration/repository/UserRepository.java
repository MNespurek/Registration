package com.example.registration.repository;

import com.example.registration.RegistrationException;
import com.example.registration.model.User;
import com.example.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@Repository
public class UserRepository {
@Autowired
User user;

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

    public String getUserFromDatabaseByUuidBasicVersion(String uuid) {
        try(ResultSet resultSet = connectionToDatabaseResultset(SettingsRepository.getUserFromDatabaseByUUID(uuid))) {

                Long id = resultSet.getLong(1);
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");

        return "Osoba s "+uuid+" má databázové id: "+id+" jméno: "+name+ " a příjmení: " +surname+".";


        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserFromDatabaseForEdit(String uuid) {
        try(ResultSet resultSet = connectionToDatabaseResultset(SettingsRepository.getUserFromDatabaseByUUID(uuid))) {
            String name = resultSet.getString(SettingsRepository.NAME);
            user.setName(name);
            String surname = resultSet.getString(SettingsRepository.SURNAME);
            user.setSurname(surname);
            return user;


        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserFromDatabaseFullVersion(String uuid) {
        try(ResultSet resultSet = connectionToDatabaseResultset(SettingsRepository.getUserFromDatabaseByUUID(uuid))) {

            Long id = resultSet.getLong(1);
            String name = resultSet.getString(SettingsRepository.NAME);
            String surname = resultSet.getString(SettingsRepository.SURNAME);
            String personId = resultSet.getString(SettingsRepository.PERSONID);
            String uniqueId = resultSet.getString(SettingsRepository.UNIQUEID);

            return "Osoba s "+uuid+" má databázové id: "+id+" jméno: "+name+ " a příjmení: " +surname+" personId: "+personId+ " uuid: "+uniqueId+".";


        } catch (RegistrationException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


