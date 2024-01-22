package app.controller;

import app.dto.Response;
import app.entity.Animal;
import app.service.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/animal-service")
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<Animal> get(){
        return animalService.getAllAnimals();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Animal addAnimal(@RequestBody Animal animal){
        return animalService.addAnimal(animal);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteAnimal(@PathVariable int id){
        return animalService.deleteAnimal(id);
    }

    @PutMapping( "/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Animal updateAnimal(@PathVariable int id,  @RequestBody Animal animal) {
        return animalService.updateAnimal(id,animal);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Animal getAnimalById(@PathVariable int id){
        return animalService.getAnimalById(id);
    }

}
