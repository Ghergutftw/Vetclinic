// AnimalServiceImpl.java
package app.service;

import app.dto.Response;
import app.entity.Animal;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.AnimalRepository;

import java.util.List;

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
        log.info("Animal added successfully: {}", animal);
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
    public Animal updateAnimal(Animal animal, int id) {
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


    private static String generateAnimalCode() {
        // Increment the counter and format the animal code
        animalCodeCounter++;
        return "AP" + String.format("%05d", animalCodeCounter);
    }

}
