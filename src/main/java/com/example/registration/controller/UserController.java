package com.example.registration.controller;

import com.example.registration.RegistrationException;
import com.example.registration.config.Settings;
import com.example.registration.model.User;
import com.example.registration.model.dto.UserDTO;
import com.example.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@RequestMapping("api/v1")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    //z venku chce mi zasílat v objektu i pohlaví a musí si s tím Controller poradit a dále pracovat. Data uložit do DB
    //vytvářet objekt přes statickou proměnnou createObject s tím, že pokud zadá nevalidní parametr, pouze dojde k upozornění ne že spadne proces
    @PostMapping("/user")
    public String saveUser(@RequestBody User user) throws RegistrationException, SQLException, FileNotFoundException {
        String statement = "";
        if (userService.setOfIdsToSelectFrom().contains(user.getPersonID())) {
            userService.saveUserToDatabase(user);
            statement = "Uživatel s uuid "+user.getUniqueId()+ " byl úspěšně uložen do databáze";
        } else if (userService.setOfIdsFromDatabase().contains(user.getPersonID()))
        {
            statement = "Zadaná hodnota "+user.getPersonID()+ " již je v databázi použita!";
        }
        else statement = "Zadaná hodnota " + user.getPersonID() + " není definována v souboru možných hodnot " + Settings.PERSONIDFILE + ".";
        return statement;
    }

    @GetMapping("/user/{ID}")
    public UserDTO getUserById(@PathVariable(value = "ID") Long id, @RequestParam(value = "detail", required = false, defaultValue = "false") Boolean detail) throws RegistrationException, SQLException {
        UserDTO userDTO = null;

        if(Boolean.FALSE.equals(detail)){
            userDTO = userService.getUserFromDatabaseByIdBasicVersion(id);
        }
        else userDTO = userService.getUserFromDatabaseByIdFullVersion(id);
        return userDTO;
    }

    @GetMapping("/users")
    public List<UserDTO> getUsers(@RequestParam(value = "detail", required = false, defaultValue = "false") Boolean detail) throws RegistrationException, SQLException {
        List<UserDTO> returnUsers;
        if(Boolean.FALSE.equals(detail)) {
            returnUsers = userService.getUsersFromDatabaseBasicVersion();
        }else returnUsers = userService.getUsersFromDatabaseFullVersion();
        return returnUsers;
    }
    @PutMapping("/user")
    public String editUser(@RequestBody User user) throws RegistrationException, SQLException {
        user = userService.changeUserNameAndSurname(user.getId(), user.getName(), user.getSurname());
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getSurname());
        return "Upravili jste uživatele s id: "+userDTO.getId()+ " nové jméno je: "+userDTO.getName()+ " a příjmení: "+userDTO.getSurname()+ ".";

    }

    @DeleteMapping("/user/{ID}")
    public String deleteUser(@PathVariable(value = "ID") Long id) throws RegistrationException, SQLException {
        userService.deleteUserFromDatabaseById(id);
        return "Uživatel s id "+id+ " by úspěšně vymazán.";
    }
}
