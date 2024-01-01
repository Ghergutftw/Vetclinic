package app.components;

import app.entity.User;
import app.enums.Roles;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        User user1 = new User(0, "test", "test@gmail.com", "test", Roles.ADMIN);
        User user2 = new User(0, "test", "test@gmail.com", "test", Roles.ADMIN);

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
