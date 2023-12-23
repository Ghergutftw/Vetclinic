package app.service;

import app.dto.Response;
import app.dto.UserLoginDTO;
import app.entity.User;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    static final String SUCCESS_MESSAGE = "success";

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public Response login(UserLoginDTO userToCheck) {
        List<User> users = userRepository.findAll();

        // Check if the user only logged in with the username if not check if he logged in with the email
        if (!userToCheck.getEmail().contains("@")) {
            for (User user : users) {
                if (user.getUsername().equals(userToCheck.getEmail()) && user.getPassword().equals(userToCheck.getPassword())) {
                    log.info("User " + user.getUsername() + " logged in");
                    return new Response(SUCCESS_MESSAGE, "Logged in");
                }
            }
        } else {
            for (User user : users) {
                if (user.getEmail().equals(userToCheck.getEmail()) && user.getPassword().equals(userToCheck.getPassword())) {
                    log.info("User " + user.getUsername() + " logged in");
                    return new Response(SUCCESS_MESSAGE, "Logged in");
                }
            }
        }
        return new Response("failed", "Wrong credentials");
    }

    @Override
    public String logout() {
        return "Logged out";
    }

    @Override
    public User getUser(int id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        log.info("Creating user: {}", user);
        return userRepository.save(user);
    }

    @Override
    public Response deleteUser(int id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
        return new Response(SUCCESS_MESSAGE, "Deleted user with id: " + id);
    }

    @Override
    public User updateUser(int id, User user) {
        log.info("Updating user with id: {}", id);
        user.setId(id);
        userRepository.save(user);
        return userRepository.findOneById(id);
    }

    @Override
    public User updatePassword(int id, String password) {
        log.info("Updating password for user with id: {}", id);
        User user = userRepository.findOneById(id);
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }


    @Override
    public String getDecodedPassword(String encodedPassword) {
        byte[] decodedPassword = Base64.getDecoder().decode(encodedPassword);
        decodedPassword = new String(decodedPassword).getBytes();
        return new String(decodedPassword);
    }

    @Override
    public String getEncodedPassword(String decodedPassword) {
        return Base64.getEncoder().encodeToString(decodedPassword.getBytes());
    }

}
