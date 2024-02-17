package app.service;

import app.dto.AnimalDTO;
import app.dto.Creation;
import app.dto.Response;
import app.dto.ConsultationCreation;
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

    byte[] getImage(String animalCode) throws IOException;

    Response adoptAnimal(String animalCode,int ownerId);

    Response abandonAnimal(int animalId);

    Animal getAnimalByCode(String animalCode);

    Creation addAnimalFromConsultation(ConsultationCreation consultationCreation);
}
