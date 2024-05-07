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
    UserService userService;

    @PostMapping("user")
    public String saveUser(@RequestBody User user) throws RegistrationException, SQLException {
        userService.saveUserToDatabase(user);
        return "Uživatel s "+user.getUniqueId()+ " byl úspěšně uložen do databáze.";
    }

    @GetMapping("/user/{ID}")
    public User getId(@PathVariable(value = "id") String id, @RequestParam(value = "detail", required = false) Boolean detail) {
        User returnUser;
        if(detail=true){
            returnUser = userService.getUserFromDatabaseByIdBasicVersion(id);
        }
        else returnUser = userService.getUserFromDatabaseByIdFullVersion(id);

        return returnUser;
    }

    @GetMapping("/users")
    public List<User> getUsersBasicVersion(@RequestParam(value = "detail", required = false) Boolean detail) {
        List<User> returnUsers;
        if(detail=true) {
            returnUsers = userService.getUsersFromDatabaseFullVersion();
        }else returnUsers = userService.getUsersFromDatabaseBasicVersion();

        return returnUsers;


    }

    @PutMapping("/user")
    public User editUser(@RequestBody String name, String surname, String id) throws RegistrationException, SQLException {
        User user = userService.changeUserNameAndSurname(name, surname, id);
        user.setName(name);
        user.setSurname(surname);
        System.out.println("Upravili jste uživatele s id: "+id+ " nové jméno je: "+user.getName()+ " a příjmení: "+user.getSurname()+ ".");
        return user;

    }

    @DeleteMapping("/user/{ID}")
    public String deleteUser(@PathVariable(value = "ID") String id) throws RegistrationException, SQLException {
        userService.deleteUserFromDatabaseById(id);
        return "Uživatel s uuid "+id+ " by úspěšně vymazán.";
    }
}
