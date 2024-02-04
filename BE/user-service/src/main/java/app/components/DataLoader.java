package app.components;

import app.entity.User;
import app.enums.Roles;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        // Create admin user
        String password = passwordEncoder.encode("test");
        User user = new User(0, "test", "Madalin", "Ghergut", "0740123456","madalinghergut@gmail.com", password, Roles.DEV);
        userRepository.save(user);
    }
}
