package app.feign;

import app.dto.ResponseDTO;
import app.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service" , url = "http://localhost:8085/user-service" )
public interface UserInterface {

    @PostMapping("/create")
    UserDTO createUser(@RequestBody UserDTO user);

    @GetMapping("/get/{id}")
    UserDTO getUser(@PathVariable int id);

    @DeleteMapping("/delete/{id}")
    ResponseDTO deleteUser(@PathVariable int id);

    @PutMapping("/update/{id}")
    UserDTO updateUser(@PathVariable int id, @RequestBody UserDTO user);

    @GetMapping("/get-all")
    List<UserDTO> getUsers();

    @PostMapping("/login")
    ResponseDTO login(@RequestBody UserDTO userLoginDTO);

    @GetMapping("/logout")
    String logout();

}
