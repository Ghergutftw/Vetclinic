package app.controller;

import app.dto.AnimalDTO;
import app.dto.Creation;
import app.dto.Response;
import app.dto.ConsultationCreation;
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

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<AnimalDTO> get() {
        return animalService.getAllAnimals();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalDTO addAnimal(@RequestBody Animal animal) {
        return animalService.addAnimal(animal);
    }

    @PostMapping("/add-from-consultation")
    @ResponseStatus(HttpStatus.CREATED)
    public Creation addAnimalFromConsultation(@RequestBody ConsultationCreation creation) {
        return animalService.addAnimalFromConsultation(creation);
    }

    @PostMapping("/post-image/{animalId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Response postImage(@RequestParam("image") MultipartFile file, @PathVariable int animalId) throws IOException {
        return animalService.saveImage(file, animalId);
    }

    @PostMapping("/abandon")
    @ResponseStatus(HttpStatus.CREATED)
    public Response abandonAnimal(@RequestParam int id) {
        return animalService.abandonAnimal(id);
    }

    @GetMapping(value = "/images", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@RequestParam String animalCode) throws IOException {
        return animalService.getImage(animalCode);
    }

    @PostMapping("/adopt")
    public Response adoptAnimal(@RequestParam String animalCode,@RequestParam int ownerId) {
        return animalService.adoptAnimal(animalCode , ownerId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteAnimal(@PathVariable int id) {
        return animalService.deleteAnimal(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AnimalDTO updateAnimal(@PathVariable int id, @RequestBody Animal animal) {
        return animalService.updateAnimal(id, animal);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Animal getAnimalById(@PathVariable int id) {
        return animalService.getAnimalById(id);
    }

    @GetMapping("/animals")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Animal getAnimalByCode(@RequestParam String animalCode) {
        return animalService.getAnimalByCode(animalCode);
    }

}
