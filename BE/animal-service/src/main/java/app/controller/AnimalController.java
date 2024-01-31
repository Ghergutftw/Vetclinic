package app.controller;

import app.dto.AnimalDTO;
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
    public List<AnimalDTO> get() {
        return animalService.getAllAnimals();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalDTO addAnimal(@RequestBody Animal animal) {
        return animalService.addAnimal(animal);
    }

    @PostMapping("/post-image/{animalId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response addAnimal(@RequestParam("image") MultipartFile file  , @PathVariable int animalId) throws IOException {
        return animalService.saveImage(file,animalId);
    }

    @PostMapping("/abandon/{animalId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response abandonAnimal(@PathVariable int animalId) {
        return animalService.abandonAnimal(animalId);
    }

    @GetMapping(value = "/get-image/{animalId}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@PathVariable int animalId) throws IOException {
        return animalService.getImage(animalId);
    }

    @PostMapping("/adopt/{animalId}")
    public Response adoptAnimal(@PathVariable int animalId){
        return animalService.adoptAnimal(animalId);
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteAnimal(@PathVariable int id){
        return animalService.deleteAnimal(id);
    }

    @PutMapping( "/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AnimalDTO updateAnimal(@PathVariable int id, @RequestBody Animal animal) {
        return animalService.updateAnimal(id,animal);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Animal getAnimalById(@PathVariable int id){
        return animalService.getAnimalById(id);
    }

}
