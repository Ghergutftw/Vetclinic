// AnimalService.java
package app.service;

import app.entity.Animal;

import java.util.List;

public interface AnimalService {

    Animal addAnimal(Animal animal);

    List<Animal> getAllAnimals();

    void deleteAnimal(int id);

    void updateAnimal(Animal animal, int id);

    Animal getAnimalById(int id);
}
