package app.service;

import app.dto.LoginResponse;
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

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public LoginResponse login(UserLoginDTO userToCheck) {
        List<User> users = userRepository.findAll();

        // Check if the user only logged in with the username if not check if he logged in with the email
        if (!userToCheck.getEmail().contains("@")) {
            for (User user : users) {
                if (user.getUsername().equals(userToCheck.getEmail()) && user.getPassword().equals(userToCheck.getPassword())) {
                    log.info("User " + user.getUsername() + " logged in");
                    return new LoginResponse("success", "Logged in");
                }
            }
        } else {
            for (User user : users) {
                if (user.getEmail().equals(userToCheck.getEmail()) && user.getPassword().equals(userToCheck.getPassword())) {
                    log.info("User " + user.getUsername() + " logged in");
                    return new LoginResponse("success", "Logged in");
                }
            }
        }
        return new LoginResponse("failed", "Wrong credentials");
    }

    @Override
    public String logout() {
        return "Logged out";
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
