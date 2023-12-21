package app.feign;

import app.dto.AnimalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${services.animal.name}" , url = "${services.animal.url}" )
public interface AnimalInterface {

//    Api-urile de la animal-service
    @PostMapping("/add")
    AnimalDTO addAnimal(@RequestBody AnimalDTO animal);

    @GetMapping("/get/{id}")
    AnimalDTO getAnimalById(@PathVariable int id);



}
