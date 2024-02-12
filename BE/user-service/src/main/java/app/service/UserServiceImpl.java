package app.service;

import app.dto.Response;
import app.dto.SignUpDTO;
import app.dto.UserDTO;
import app.dto.UserLoginDTO;
import app.entity.User;
import app.enums.Roles;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

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
        // Find user by username or email
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(userToCheck.getEmail(), userToCheck.getEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            // Check if entered password matches the stored hashed password
            if (passwordEncoder.matches(userToCheck.getPassword(), user.getPassword())) {
                log.info("User " + user.getUsername() + " logged in");
                return new Response(SUCCESS_MESSAGE, "Logged in");
            }
        }

        return new Response("failed", "Wrong credentials");
    }

    @Override
    public Response logout() {
        return new Response(SUCCESS_MESSAGE, "Logged out");
    }

    @Override
    public User getUser(int id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Response createUser(SignUpDTO sign) {
        User user = new User();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(sign.getPassword());
        if (user.getRole()==null){
            user.setRole(Roles.OWNER);
        }
        user.setPassword(encodedPassword);
        user.setUsername(sign.getUsername());
        user.setEmail(sign.getEmail());
        user.setFirstName(sign.getFirstName());
        user.setLastName(sign.getLastName());
        user.setPhoneNumber(sign.getPhoneNumber());

        userRepository.save(user);
        return new Response("success", "User created");
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
    public UserDTO getUserByUsername(String username) {
        log.info("Fetching user with username: {}", username);
        User oneByUsername = userRepository.findOneByUsername(username);
        return UserDTO.builder()
                .id(oneByUsername.getId())
                .username(oneByUsername.getUsername())
                .firstName(oneByUsername.getFirstName())
                .lastName(oneByUsername.getLastName())
                .phoneNumber(oneByUsername.getPhoneNumber())
                .email(oneByUsername.getEmail())
                .role(oneByUsername.getRole())
                .build();
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
