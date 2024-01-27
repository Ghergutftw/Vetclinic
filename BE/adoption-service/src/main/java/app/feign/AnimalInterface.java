package app.feign;

import app.dto.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "animal-service" , url = "http://localhost:8081/animal-service" )
public interface AnimalInterface {

    @PostMapping("/adopt/{animalId}")
    Response adopt(@PathVariable int animalId);

}
