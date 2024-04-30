package com.example.registration.controller;

import com.example.registration.RegistrationException;
import com.example.registration.model.User;
import com.example.registration.repository.UserRepository;
import com.example.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



@RequestMapping("api/v1")
@RestController
public class UserController {
    @Autowired
    UserService user;
    UserRepository userRepository;
    @PostMapping("user")
    public String saveUser(@RequestBody User user) throws RegistrationException, SQLException {
        userRepository.saveUserToDatabase(user);
        return "Uživatel s "+user.getUniqueId()+ " byl úspěšně uložen do databáze.";
    }

    @GetMapping("/user/{ID}")
    public String getId(@PathVariable(value = "id")String id) {


    }

    @GetMapping("/users/{ID}?detail=true")
    public String getIdFullVersion(@PathVariable(value="id")String id, @RequestParam(value = "detail") Boolean detail) {

    }

    @GetMapping("/users/{ID}")
    public String getIDFullVersion()



}
