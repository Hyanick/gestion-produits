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

    public User registerUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public boolean authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Mot de passe entré : " + password);
            System.out.println("Mot de passe hashé en base : " + user.getPassword());
            return true;
        }
        System.out.println("Mot de passe entré : " + password);
        System.out.println("Mot de passe hashé en base : " + user.getPassword());

        return false;
    }
}