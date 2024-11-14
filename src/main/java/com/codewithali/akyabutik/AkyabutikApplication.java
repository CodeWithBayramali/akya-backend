package com.codewithali.akyabutik;

import com.codewithali.akyabutik.model.Role;
import com.codewithali.akyabutik.model.User;
import com.codewithali.akyabutik.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class AkyabutikApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AkyabutikApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(AkyabutikApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail("akyabutik@gmail.com");
        if (userOptional.isEmpty()) {
            userRepository.save(new User(
               null,
                    "akyabutik@gmail.com",
                    passwordEncoder.encode("wweeqq11"),
                    "Kübra",
                    Set.of(Role.ADMIN)
            ));
        }
    }
}
