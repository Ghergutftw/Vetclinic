package app.controller;

import app.dto.Response;
import app.entity.Animal;
import app.service.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public Animal addAnimal(@RequestBody Animal animal) {
        return animalService.addAnimal(animal);
    }

    @PostMapping("/post-image/{animalId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String addAnimal(@RequestParam("image") MultipartFile file  , @PathVariable int animalId) throws IOException {
        return animalService.saveImage(file,animalId);
    }


//    If you want to save the image to see it remove the produces stuff
    @GetMapping(value = "/get-image/{animalId}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable int animalId) throws IOException {
        return animalService.getImage(animalId);
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
