package fr.ecommerce.backend.service;

import fr.ecommerce.backend.model.User;
import fr.ecommerce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser (User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("email : " + user.getEmail());
        System.out.println("password : " + user.getPassword());
        System.out.println("name : " + user.getName());
        System.out.println("user : " + user);
        userRepository.save(user);
        return user;
    }

    public User  findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }





/*
    public User registerUser (String email, String password, String name) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        System.out.println("email : " + email);
        System.out.println("password : " + password);
        System.out.println("name : " + name);
        System.out.println("user : " + user);

         userRepository.save(user);
        return user;
    }
*/

    /*
    public boolean authenticate(String email, String password) {
        Optional <User>  user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Mot de passe entré : " + password);
            System.out.println("Mot de passe hashé en base : " + user.getPassword());
            return true;
        }
        System.out.println("Mot de passe entré : " + password);
        System.out.println("Mot de passe hashé en base : " + user.getPassword());

        return false;
    }
    /*
     */
}