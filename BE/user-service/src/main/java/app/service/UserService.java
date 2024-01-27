package app.service;

import app.dto.Response;
import app.dto.SignUpDTO;
import app.dto.UserLoginDTO;
import app.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    Response login(UserLoginDTO userToCheck);

    Response logout();

    String getDecodedPassword(String encodedPassword);

    String getEncodedPassword(String decodedPassword);

    User getUser(int id);

    Response createUser(SignUpDTO sign);

    Response deleteUser(int id);

    User updateUser(int id, User user);

    User updatePassword(int id, String password);

    User getUser(String username);
}
