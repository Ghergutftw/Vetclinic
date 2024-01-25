package app.controller;

import app.dto.Response;
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
@RequestMapping("/user-service")
public class UserController {

    private final UserService userService;

    @PostMapping("/update-password/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public User updatePassword(@PathVariable int id, @RequestBody String password){
        return userService.updatePassword(id, password);
    }
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable int id){
        return userService.getUser(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteUser(@PathVariable int id){
        return userService.deleteUser(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@PathVariable int id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<User> getUsers(){
       return userService.getAllUsers();
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response login(@RequestBody UserLoginDTO userLoginDTO){
        return userService.login(userLoginDTO);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response logout(){
        return userService.logout();
    }

}
