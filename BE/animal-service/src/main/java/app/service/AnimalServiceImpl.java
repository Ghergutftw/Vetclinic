// AnimalServiceImpl.java
package app.service;

import app.dto.Response;
import app.entity.Animal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.AnimalRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Slf4j
public class AnimalServiceImpl implements AnimalService {

    private static int animalCodeCounter = 0;
    private static final String IMAGES_FOLDER = "animal-service/src/main/resources/images/animals/";
    private final AnimalRepository animalRepository;


    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Animal addAnimal(Animal animal) {
        animal.setAnimalCode(generateAnimalCode());
        animalRepository.save(animal);
        log.info("Animal with id added successfully: {}", animal.getId());
        return animal;
    }

    @Override
    public List<Animal> getAllAnimals() {
        log.info("Fetching all animals");
        return animalRepository.findAll();
    }

    @Override
    public Response deleteAnimal(int id) {
        log.info("Deleting animal with id: {}", id);
        animalRepository.deleteById(id);
        return new Response("succes", "Deleted animal with id: " + id);
    }

    @Override
    public Animal updateAnimal(int id, Animal animal) {
        log.info("Updating animal with id: {}", id);
        animal.setId(id);
        animalRepository.save(animal);
        return animalRepository.findOneById(id);
    }

    @Override
    public Animal getAnimalById(int id) {
        log.info("Fetching animal with id: {}", id);
        return animalRepository.findById(id).orElseThrow();
    }

    @Override
    public String saveImage(MultipartFile file, int animalId) throws IOException {
        String fileName = file.getOriginalFilename();
        Path targetPath = Path.of(IMAGES_FOLDER, fileName);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        Animal animal = animalRepository.findOneById(animalId);
        String path = IMAGES_FOLDER + fileName;
        log.info("Saving image for animal with id: {} with path : {}", animalId, path);
        animal.setPathToImage(path);
        animalRepository.save(animal);
        return "Uploaded image successfully";
    }

    @Override
    public byte[] getImage(int animalId) throws IOException {
        Animal animal = animalRepository.findOneById(animalId);
        Path path = Path.of(animal.getPathToImage());
        return Files.readAllBytes(path);
    }


    private static String generateAnimalCode() {
        // Increment the counter and format the animal code
        animalCodeCounter++;
        return "AP" + String.format("%05d", animalCodeCounter);
    }

}
