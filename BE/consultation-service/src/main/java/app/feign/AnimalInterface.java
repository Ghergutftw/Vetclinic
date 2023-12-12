package app.feign;

import app.dto.AnimalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ANIMAL-SERVICE" , url = "http://localhost:8080/animal-service" )
public interface AnimalInterface {

    @PostMapping("/create")
    AnimalDTO addAnimal(@RequestBody AnimalDTO animal);

    @GetMapping("/get/{id}")
    AnimalDTO getAnimalById(@PathVariable int id);


}
