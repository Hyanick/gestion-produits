package fr.ecommerce.backend.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import fr.ecommerce.backend.model.User;
import fr.ecommerce.backend.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private JwtDecoder jwtDecoder;

    public enum GrantType {
        refreshToken,
        password
    }

    @Setter
    @Getter
    public static class JwtRequest {
        private String username;
        private String password;
        private String refreshToken;
        private GrantType grantType;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public GrantType getGrantType() {
            return grantType;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public void setGrantType(GrantType grantType) {
            this.grantType = grantType;
        }
    }


    @Setter
    @Getter
    public static class JwtResponse {
        public JwtResponse(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        private String accessToken;
        private String refreshToken;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }

    @PostMapping("authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest request) {
        System.out.println("request sending : " + request.getUsername());
        switch (request.getGrantType()) {
            case password:
                return authenticate(request.getUsername(), request.getPassword());
            case refreshToken:
                return refresh(request.getRefreshToken());
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid grant type");
        }
    }

    private JwtResponse authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
                //.orElseThrow(() -> new BadCredentialsException("email or password is invalid"));
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("email or password is invalid");
        return new JwtResponse(
                generateAccessToken(user),
                generateRefreshToken(user));
    }

    private JwtResponse refresh(String refreshToken) {
        var jwt = jwtDecoder.decode(refreshToken);
        User user = userRepository.findById(Long.parseLong(jwt.getSubject())).get();
        return new JwtResponse(
                generateAccessToken(user),
                generateRefreshToken(user));
    }

    private String generateAccessToken(User user) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .issuer("products-backend")
                .expiresAt(Instant.now().plusSeconds(5 * 60))
                .subject(String.valueOf(user.getId()))
                .claim("username", user.getName())
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    private String generateRefreshToken(User user) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(Instant.now())
                .issuer("products-backend")
                .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .subject(String.valueOf(user.getId()))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }
}

