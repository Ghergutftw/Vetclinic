package app.feign;

import app.dto.ResponseDTO;
import app.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service" , url = "http://localhost:8085/user-service" )
public interface UserInterface {

    @PostMapping("")
    UserDTO createUser(@RequestBody UserDTO user);

    @GetMapping("/{id}")
    UserDTO getUser(@PathVariable int id);

    @DeleteMapping("/{id}")
    ResponseDTO deleteUser(@PathVariable int id);

    @PutMapping("/{id}")
    UserDTO updateUser(@PathVariable int id, @RequestBody UserDTO user);

    @GetMapping("")
    List<UserDTO> getUsers();

    @PostMapping("/login")
    ResponseDTO login(@RequestBody UserDTO userLoginDTO);

    @GetMapping("/logout")
    String logout();

}
