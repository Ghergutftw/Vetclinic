// AnimalServiceImpl.java
package app.service;

import app.entity.Animal;
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
        return animal;
    }

    private String generateAnimalCode() {
        // Increment the counter and format the animal code
        animalCodeCounter++;
        return "AP" + String.format("%05d", animalCodeCounter);
    }

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public void deleteAnimal(int id) {
        animalRepository.deleteById(id);
    }

    @Override
    public void updateAnimal(Animal animal, int id) {
        animal.setId(id);
        animalRepository.save(animal);
    }

    @Override
    public Animal getAnimalById(int id) {
        return animalRepository.findById(id).orElseThrow();
    }
}
