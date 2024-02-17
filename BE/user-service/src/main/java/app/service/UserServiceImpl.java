package app.service;

import app.dto.*;
import app.entity.User;
import app.enums.Roles;
import app.feign.EmailInterface;
import app.feign.OwnerInterface;
import app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    static final String SUCCESS_MESSAGE = "success";

    private final UserRepository userRepository;

    OwnerInterface ownerInterface;

    EmailInterface emailInterface;

    public UserServiceImpl(UserRepository userRepository, OwnerInterface ownerInterface, EmailInterface emailInterface) {
        this.userRepository = userRepository;
        this.ownerInterface = ownerInterface;
        this.emailInterface = emailInterface;
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public Response login(UserLoginDTO userToCheck) {
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(userToCheck.getEmail(), userToCheck.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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
        if (userRepository.existsByUsernameOrEmailOrPhoneNumber(sign.getUsername(), sign.getEmail(), sign.getPhoneNumber())) {
            return new Response("failed", "Username is already taken");
        } else {

            User user = new User();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(sign.getPassword());
            if (user.getRole() == null) {
                user.setRole(Roles.OWNER);
            }
            user.setPassword(encodedPassword);
            user.setUsername(sign.getUsername());
            user.setEmail(sign.getEmail());
            user.setFirstName(sign.getFirstName());
            user.setLastName(sign.getLastName());
            user.setPhoneNumber(sign.getPhoneNumber());

            userRepository.save(user);
            return new Response(SUCCESS_MESSAGE, "User created");
        }
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
    public UserDTO getUserByEmail(String email) {
        log.info("Fetching user with email: {}", email);

        User oneByEmail = userRepository.findOneByEmail(email);

        if (oneByEmail == null) {
            OwnerDTO ownerDTO = ownerInterface.getOwnerByEmail(email);
            User toBeSavedUser = createUserFromOwner(ownerDTO);
            oneByEmail = userRepository.save(toBeSavedUser);
            emailInterface.sendEmailWithNewAccountInfo(email,new AccountInfo(toBeSavedUser.getLastName() + "." + toBeSavedUser.getFirstName(), toBeSavedUser.getLastName() + "." + ownerDTO.getFirstName()));
        }

        return UserDTO.builder()
                .id(oneByEmail.getId())
                .username(oneByEmail.getUsername())
                .firstName(oneByEmail.getFirstName())
                .lastName(oneByEmail.getLastName())
                .phoneNumber(oneByEmail.getPhoneNumber())
                .email(oneByEmail.getEmail())
                .role(oneByEmail.getRole())
                .build();

    }



    @Override
    public UserDTO getUserByUsername(String username) {
        log.info("Fetching user with username: {}", username);
        User oneByUsername = userRepository.findOneByUsername(username);
        if (oneByUsername == null) {
            return new UserDTO(1, "test", "test", "test", "12312123312312", "test@gmail.com", "Aasdada", Roles.ADMIN);
        }
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


    private static User createUserFromOwner(OwnerDTO ownerDTO) {
        User toBeSavedUser = new User();
        toBeSavedUser.setEmail(ownerDTO.getEmail());
        toBeSavedUser.setRole(Roles.OWNER);
        toBeSavedUser.setLastName(ownerDTO.getLastName());
        toBeSavedUser.setFirstName(ownerDTO.getFirstName());
        toBeSavedUser.setUsername(ownerDTO.getLastName() + "." + ownerDTO.getFirstName());
        toBeSavedUser.setPassword(ownerDTO.getLastName() + "." + ownerDTO.getFirstName());
        toBeSavedUser.setPhoneNumber(ownerDTO.getPhoneNumber());
        return toBeSavedUser;
    }
}
