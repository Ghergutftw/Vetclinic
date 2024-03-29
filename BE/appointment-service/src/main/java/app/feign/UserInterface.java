package app.feign;

import app.dto.OwnerDTO;
import app.dto.Response;
import app.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "user-service" , url = "http://localhost:8085/user-service" )
public interface UserInterface {

    @GetMapping("/users/username")
    UserDTO getUserByUsername(@RequestParam String username);

    @GetMapping("/users/email")
    UserDTO  getUserByEmail(@RequestParam String email);

    @PostMapping("")
    Response saveUser(OwnerDTO owner);
}
