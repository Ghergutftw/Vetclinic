package app.controller;

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
    public List<Animal> get(){
        return animalService.getAllAnimals();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Animal addAnimal(@RequestBody Animal animal){
        return animalService.addAnimal(animal);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAnimal(@PathVariable int id){
        animalService.deleteAnimal(id);
    }

    @PutMapping( "/update/{id}")
    public void updateAnimal(@RequestBody Animal animal, @PathVariable int id) {
        animalService.updateAnimal(animal,id);
    }

    @GetMapping("/get/{id}")
    public Animal getAnimalById(@PathVariable int id){
        return animalService.getAnimalById(id);
    }

}
