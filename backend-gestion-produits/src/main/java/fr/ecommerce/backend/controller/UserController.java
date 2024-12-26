package fr.ecommerce.backend.controller;

import fr.ecommerce.backend.model.User;
import fr.ecommerce.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
/*
   @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password, @RequestParam String name) {
        userService.registerUser(email, password, name);
        return "Utilisateur enregistré avec succès.";
    }
*/

    @PostMapping("/register")
    public User register(@RequestBody User user) {
      user =   userService.registerUser(user);
        return user;
    }


   /* @PostMapping("/login")
    public boolean login(@RequestParam String email, @RequestParam String password) {
        return   userService.authenticate(email, password);
    }
    /*
       // Optional<User> userOptional = userService.findByEmail(email);

     /*   if (userOptional.isPresent() && password.equals(userOptional.get().getPassword())) {
            return "Connexion réussie.";
        } else {
            return "Échec de la connexion.";
        }
    }*/
/*
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("Mot de passe entré : " + password);
                System.out.println("Mot de passe hashé en base : " + user.getPassword());
                return "Connexion réussie.";
            } else {
                System.out.println("Mot de passe entré : " + password);
                System.out.println("Mot de passe hashé en base : " + user.getPassword());
                return "Mot de passe incorrect.";
            }
        }
        return "Utilisateur non trouvé.";
    }

 */
}