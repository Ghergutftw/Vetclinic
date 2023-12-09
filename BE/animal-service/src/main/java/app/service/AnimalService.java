package app.service;


import app.entity.Animal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import app.repository.AnimalRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal addAnimal(Animal animal) {
        animalRepository.save(animal);
        return animal;
    }

    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

     
    public void deleteAnimal(int id) {
        animalRepository.deleteById(id);
    }

     
    public void updateAnimal(Animal animal, int id) {
        animal.setId(id);
        animalRepository.save(animal);
    }

    public Animal getAnimalById(int id) {
        return animalRepository.findById(id).orElseThrow();
    }
}

