package com.example.registration.service;

import com.example.registration.RegistrationException;
import com.example.registration.config.Settings;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    private String name;

    private String surname;

    private String personId;

    private UUID uniqueId;


    public UserService(String name, String surname, String personId, UUID uniqueId) {
        this.name = name;
        this.surname = surname;
        this.personId = personId;
        this.uniqueId = uniqueId;
    }



    public Set<String> setOfIdsFromFile() throws FileNotFoundException, SQLException, RegistrationException {
        Set<String> setFromDataPersonId = new HashSet<>();

        try(Scanner scanner = new Scanner(new BufferedReader(new FileReader(Settings.getPersonIdFile())))) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String personIdFromFile = scanner.nextLine();
                setFromDataPersonId.add(personIdFromFile);

            }
            return setFromDataPersonId;


        }catch (FileNotFoundException e) {
            throw new RegistrationException("Soubor "+Settings.getPersonIdFile()+ "nebyl nalezen!" +e);
        }
    }


}
