package com.tekerasoft.arzuamber;

import com.tekerasoft.arzuamber.model.Role;
import com.tekerasoft.arzuamber.model.User;
import com.tekerasoft.arzuamber.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@SpringBootApplication
public class ArzuamberApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ArzuamberApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(ArzuamberApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> userOptional = userRepository.findByEmail("akyabutik-admin@gmail.com");
        if(userOptional.isEmpty()) {
            userRepository.save(new User(
                    "Kübra",
                    "DURSUN",
                    "akyabutik-admin@gmail.com",
                    passwordEncoder.encode("Wweeqq11."),
                    setOf(Role.ADMIN)
            ));
        }
    }
}
