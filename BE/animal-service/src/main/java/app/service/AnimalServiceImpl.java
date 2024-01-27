// AnimalServiceImpl.java
package app.service;

import app.dto.Response;
import app.entity.Animal;
import app.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.AnimalRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AnimalServiceImpl implements AnimalService {

    private static int animalCodeCounter = 0;
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
    public Response saveImage(MultipartFile file, int animalId) throws IOException {
        byte[] imageData = ImageUtils.compressImage(file.getBytes()); // Compress the image data
        Animal animal = animalRepository.findOneById(animalId);
        animal.setImageData(imageData);
        log.info("Saving image for animal with id: {}", animalId);
        animalRepository.save(animal);

        return new Response("success", "Image saved successfully");
    }

    @Override
    public byte[] getImage(int animalId) throws IOException {
        Animal animal = animalRepository.findOneById(animalId);
        return ImageUtils.decompressImage(animal.getImageData());
    }

    @Override
    public Response adoptAnimal(int animalId) {
        Animal animal = animalRepository.findOneById(animalId);
        animal.setForAdoption(false);
        animalRepository.save(animal);
        return new Response("success", "Animal adopted successfully");
    }


    private static String generateAnimalCode() {
        // Increment the counter and format the animal code
        animalCodeCounter++;
        return "AP" + String.format("%05d", animalCodeCounter);
    }

}
