// AnimalService.java
package app.service;

import app.dto.Response;
import app.entity.Animal;

import java.util.List;

public interface AnimalService {

    Animal addAnimal(Animal animal);

    List<Animal> getAllAnimals();

    Response deleteAnimal(int id);

    Animal updateAnimal(Animal animal, int id);

    Animal getAnimalById(int id);
}
