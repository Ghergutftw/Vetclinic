package app.controller;

import app.dto.LoginResponse;
import app.dto.UserLoginDTO;
import app.entity.User;
import app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/${services.user.name}")
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all")
    public List<User> getUsers(){
       return userService.getAllUsers();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody UserLoginDTO userLoginDTO){
        return userService.login(userLoginDTO);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public String logout(){
        return userService.logout();
    }

}
