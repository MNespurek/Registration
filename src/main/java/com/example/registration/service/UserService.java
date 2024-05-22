package com.example.registration.service;

import com.example.registration.RegistrationException;
import com.example.registration.config.Settings;
import com.example.registration.model.User;
import com.example.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.*;

@Service

public class UserService {
@Autowired
UserRepository userRepository;

    public void saveUserToDatabase(User user) throws RegistrationException, SQLException {
        userRepository.saveUserToDatabase(user);
    }
    public User getUserFromDatabaseByIdBasicVersion(Long id) {
        User userFromRepository = userRepository.getUserFromDatabaseByIdBasicVersion(id);
        return userFromRepository;
    }

    public User getUserFromDatabaseByIdFullVersion(Long id) {
        User userFromRepository = userRepository.getUserFromDatabaseFullVersion(id);
        return userFromRepository;

    }

    public List<User> getUsersFromDatabaseBasicVersion() {
        List<User> users = userRepository.getUsersFromDatabaseBasicVersion();
        return users;
    }

    public List<User> getUsersFromDatabaseFullVersion() {
        List<User> users = userRepository.getUsersFromDatabaseFullVersion();
        return users;
    }




    public User changeUserNameAndSurname(Long id, String name, String surname) throws RegistrationException, SQLException {
        User userFromRepository = userRepository.getUserFromDatabaseByIdBasicVersion(id);
        userFromRepository.setName(name);
        userFromRepository.setSurname(surname);
        userRepository.saveEditUserToDatabase(userFromRepository);
        return userFromRepository;
    }

    public String deleteUserFromDatabaseById(Long id) throws RegistrationException, SQLException {
        userRepository.deleteUserFromDatabase(id);
        return "Uživatel s "+id+ "byl vymazán z databáze.";
    }


    public Set<String> setOfIdsFromFile() throws FileNotFoundException, SQLException, RegistrationException {
        Set<String> setFromDataPersonId = new HashSet<>();

        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(Settings.PERSONIDFILE)))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String personIdFromFile = scanner.nextLine();
                setFromDataPersonId.add(personIdFromFile);

            }
            return setFromDataPersonId;


        }catch (FileNotFoundException e) {
            throw new RegistrationException("Soubor "+Settings.PERSONIDFILE+ "nebyl nalezen!" +e);
        }
    }

    public Set<String> setOfIdsFromDatabase() throws RegistrationException, SQLException {
        return userRepository.setOfPersonIDsFromDatabase();
    }

    public Set<String> setOfIdsToSelectFrom() throws RegistrationException, SQLException, FileNotFoundException {
        Set<String> setOfIdsFromDatabase = setOfIdsFromDatabase();
        Set<String> setOfIdsFromFile = setOfIdsFromFile();
        setOfIdsFromFile.removeAll(setOfIdsFromDatabase);
        Set<String> setOfIdsToSelectFrom = setOfIdsFromFile;

        return setOfIdsToSelectFrom;
    }

}
