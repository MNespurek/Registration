package com.example.registration.controller;

import com.example.registration.RegistrationException;
import com.example.registration.config.Settings;
import com.example.registration.model.User;
import com.example.registration.model.dto.UserBasicDTO;
import com.example.registration.model.dto.UserDTO;
import com.example.registration.model.dto.UserFullDTO;
import com.example.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;


//z venku chce mi zasílat v objektu i pohlaví a musí si s tím Controller poradit a dále pracovat. Data uložit do DB
//vytvářet objekt přes statickou proměnnou createObject s tím, že pokud zadá nevalidní parametr, pouze dojde k upozornění ne že spadne proces

@RequestMapping("api/v1")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public String saveUser(@RequestBody User user) throws RegistrationException, SQLException, FileNotFoundException {
        System.out.println(user.getPersonID());
        System.out.println(userService.setOfIdsFromDatabase());
        String statement = "";
        if (userService.setOfIdsToSelectFrom().contains(user.getPersonID())) {
            userService.saveUserToDatabase(user);
            statement = "Uživatel s "+user.getUniqueId()+ "byl úspěšně uložen do databáze";
        } else if (userService.setOfIdsFromDatabase().contains(user.getPersonID()))
        {
            statement = "Zadaná hodnota "+user.getPersonID()+ " již je v databázi použita!";
        }
        else statement = "Zadaná hodnota " + user.getPersonID() + " není definována v souboru možných hodnot " + Settings.PERSONIDFILE + ".";
        return statement;
    }



    @GetMapping("/user/{ID}")
    public UserDTO getUserById(@PathVariable(value = "ID") Long id, @RequestParam(value = "detail", required = false, defaultValue = "false") Boolean detail) throws RegistrationException, SQLException {
        UserDTO finalUser;
        User user;
        if(Boolean.FALSE.equals(detail)){
            user = userService.getUserFromDatabaseByIdBasicVersion(id);
            finalUser = new UserBasicDTO(user.getId(), user.getName(), user.getSurname());
        }
        else {
            user = userService.getUserFromDatabaseByIdFullVersion(id);
            finalUser = new UserFullDTO(user.getId(), user.getName(), user.getSurname(), user.getPersonID(), user.getUniqueId());
        }
        return finalUser;
    }

    @GetMapping("/users")
    public List<UserDTO> getUsersBasicVersion(@RequestParam(value = "detail", required = false, defaultValue = "false") Boolean detail) {
        List<UserDTO> returnUsers;
        if(Boolean.FALSE.equals(detail)) {
            returnUsers = userService.getUsersFromDatabaseBasicVersion();
        }else returnUsers = userService.getUsersFromDatabaseFullVersion();

        return returnUsers;
    }

    @PutMapping("/user")
    public User editUser(@RequestBody Long id, String name, String surname) throws RegistrationException, SQLException {
        User user = userService.changeUserNameAndSurname(id, name, surname);
        user.setName(name);
        user.setSurname(surname);
        System.out.println("Upravili jste uživatele s id: "+id+ " nové jméno je: "+user.getName()+ " a příjmení: "+user.getSurname()+ ".");
        return user;

    }

    @DeleteMapping("/user/{ID}")
    public String deleteUser(@PathVariable(value = "ID") Long id) throws RegistrationException, SQLException {
        userService.deleteUserFromDatabaseById(id);
        return "Uživatel s uuid "+id+ " by úspěšně vymazán.";
    }
}
