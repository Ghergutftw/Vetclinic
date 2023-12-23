package app.service;

import app.dto.Response;
import app.dto.UserLoginDTO;
import app.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    Response login(UserLoginDTO userToCheck);

    String logout();

    String getDecodedPassword(String encodedPassword);

    String getEncodedPassword(String decodedPassword);

    User getUser(int id);

    User createUser(User user);

    Response deleteUser(int id);

    User updateUser(int id, User user);

    User updatePassword(int id, String password);
}
