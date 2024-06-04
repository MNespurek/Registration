package com.example.registration.service;

import com.example.registration.RegistrationException;
import com.example.registration.config.Settings;
import com.example.registration.model.User;
import com.example.registration.model.dto.UserDTO;
import com.example.registration.model.dto.UserFullDTO;
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
    public UserDTO getUserFromDatabaseByIdBasicVersion(Long id) throws RegistrationException, SQLException {
        User userFromRepository = userRepository.getUserFromDatabaseByIdBasicVersion(id);
        UserDTO userDTO = new UserDTO(userFromRepository.getId(), userFromRepository.getName(), userFromRepository.getSurname());
        return userDTO;
    }
    public UserDTO getUserFromDatabaseByIdFullVersion(Long id) throws RegistrationException, SQLException {
        User userFromRepository = userRepository.getUserFromDatabaseFullVersion(id);
        UserDTO userDTO = new UserFullDTO(userFromRepository.getId(), userFromRepository.getName(), userFromRepository.getSurname(), userFromRepository.getPersonID(), userFromRepository.getUniqueId());
        return userDTO;
    }
    public List<UserDTO> getUsersFromDatabaseBasicVersion() throws RegistrationException, SQLException {
        List<User> users = userRepository.getUsersFromDatabaseBasicVersion();
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User user : users) {
            UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSurname());
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }
    public List<UserDTO> getUsersFromDatabaseFullVersion() {
        List<User> users = userRepository.getUsersFromDatabaseFullVersion();
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User user : users) {
            UserDTO userDTO = new UserFullDTO(user.getId(), user.getName(), user.getSurname(), user.getPersonID(), user.getUniqueId());
            usersDTO.add(userDTO);
        }
        return usersDTO;
    }
    public User changeUserNameAndSurname(Long id, String name, String surname) throws RegistrationException, SQLException {
        User userFromRepository = userRepository.getUserFromDatabaseByIdBasicVersion(id);
        userFromRepository.setName(name);
        userFromRepository.setSurname(surname);
        userRepository.saveEditUserToDatabase(userFromRepository);
        return userFromRepository;
    }
    public void deleteUserFromDatabaseById(Long id) throws RegistrationException {
        userRepository.deleteUserFromDatabase(id);
    }
    public Set<String> setOfIdsFromFile() throws RegistrationException {
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
    public Set<String> setOfIdsFromDatabase() throws RegistrationException {
        return userRepository.setOfPersonIDsFromDatabase();
    }
    public Set<String> setOfIdsToSelectFrom() throws RegistrationException {
        Set<String> setOfIdsFromDatabase = setOfIdsFromDatabase();
        Set<String> setOfIdsFromFile = setOfIdsFromFile();
        setOfIdsFromFile.removeAll(setOfIdsFromDatabase);
        Set<String> setOfIdsToSelectFrom = setOfIdsFromFile;

        return setOfIdsToSelectFrom;
    }
}
