package com.example.registration.controller;

import com.example.registration.RegistrationException;
import com.example.registration.model.User;
import com.example.registration.repository.SettingsRepository;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


@RequestMapping("api/v1")
@RestController
public class UserController {
    @Autowired
    User user;
    UserRepository userRepository;
    @PostMapping("user")
    public String saveUser(@RequestBody User user) throws RegistrationException, SQLException {
        userRepository.saveUserToDatabase(user);
        return "Uživatel s "+user.getUniqueId()+ " byl úspěšně uložen do databáze.";
    }

    @GetMapping("/user/{ID}")
    public String getId(@PathVariable(value = "id") String uuid, @RequestParam(value = "detail", required = false) Boolean detail) {
        String returnStatement = "";
        if(detail=true){
            returnStatement = userRepository.getUserFromDatabaseFullVersion(uuid);
        }
        else returnStatement = userRepository.getUserFromDatabaseByUuidBasicVersion(uuid);

        return returnStatement;
    }

    /*@GetMapping("/users/{ID}?detail=true")
    public String getIdFullVersion(@PathVariable(value="id")String uuid, @RequestParam(value = "detail") Boolean detail) {
        return userRepository.getUserFromDatabaseFullVersion(uuid);
    }*/

    @GetMapping("/users")
    public List<User> getUsersBasicVersion(@RequestBody User user, @RequestParam(value = "detail", required = false) Boolean detail) {



    }

    @PutMapping("/user/{ID}")
    public String editUser(@RequestBody String name, String surname, @PathVariable(value = "ID") String uuid) {
        User user = userRepository.getUserFromDatabaseForEdit(uuid);
        user.setName(name);
        user.setSurname(surname);
        return "Upravili jste uživatele s uuid: "+uuid+ " nové jméno je: "+name+ " a příjmení: "+surname+ ".";

    }

    @DeleteMapping("/user/{ID}")
    public String deleteUser(@PathVariable(value = "ID") String uuid) {
        SettingsRepository.deleteUserFromDatabaseByUUID(uuid);
        return "Uživatel s uuid "+uuid+ " by úspěšně vymazán.";
    }
}
