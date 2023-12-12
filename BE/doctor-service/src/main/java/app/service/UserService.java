package app.service;

import app.DTO.LoginResponse;
import app.DTO.UserLoginDTO;
import app.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    LoginResponse login(UserLoginDTO userToCheck);

    String logout();

    String getDecodedPassword(String encodedPassword);

    String getEncodedPassword(String decodedPassword);
}
