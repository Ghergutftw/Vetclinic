package app.feign;

import app.dto.AnimalDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "animal-service" , url = "http://localhost:8081/animal-service" )
public interface AnimalInterface {

//    Api-urile de la animal-service
@PostMapping("")
    AnimalDTO addAnimal(@RequestBody AnimalDTO animal);

    @GetMapping("/{id}")
    AnimalDTO getAnimalById(@PathVariable int id);



}
