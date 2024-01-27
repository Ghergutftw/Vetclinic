package app.feign;

import app.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service" , url = "http://localhost:8085/user-service" )
public interface UserInterface {

    @GetMapping("/get-user/{username}")
    UserDTO getUser(@PathVariable String username);

}
