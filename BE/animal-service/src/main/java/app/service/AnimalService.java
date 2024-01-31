// AnimalService.java
package app.service;

import app.dto.AnimalDTO;
import app.dto.Response;
import app.entity.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnimalService {

    AnimalDTO addAnimal(Animal animal);

    List<AnimalDTO> getAllAnimals();

    Response deleteAnimal(int id);

    AnimalDTO updateAnimal(int id, Animal animal);

    Animal getAnimalById(int id);

    Response saveImage(MultipartFile file, int animalId) throws IOException;

    byte[] getImage(int animalId) throws IOException;

    Response adoptAnimal(int animalId);

    Response abandonAnimal(int animalId);
}
