package app.feign;

import app.dto.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "animal-service" , url = "http://localhost:8081/animal-service" )
public interface AnimalInterface {

    @PostMapping("/adopt")
    Response adopt(@RequestParam int animalId, @RequestParam int ownerId);

}
