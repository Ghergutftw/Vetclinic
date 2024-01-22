package app.feign;

import app.dto.AnimalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "animal-service" , url = "http://localhost:8081/animal-service" )
public interface AnimalInterface {

//    Api-urile de la animal-service
    @PostMapping("/create")
    AnimalDTO addAnimal(@RequestBody AnimalDTO animal);

    @GetMapping("/get/{id}")
    AnimalDTO getAnimalById(@PathVariable int id);



}
