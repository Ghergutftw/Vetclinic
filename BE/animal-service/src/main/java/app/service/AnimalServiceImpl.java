package app.service;

import app.dto.AnimalDTO;
import app.dto.Response;
import app.entity.Animal;
import app.repository.AnimalRepository;
import app.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AnimalServiceImpl implements AnimalService {

    private static int animalCodeCounter = 0;
    ModelMapper modelMapper = new ModelMapper();
    private final AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public AnimalDTO addAnimal(Animal animal) {
        animal.setAnimalCode(generateAnimalCode());
        animalRepository.save(animal);
        log.info("Animal with id added successfully: {}", animal.getId());
        return modelMapper.map(animal, AnimalDTO.class);
    }

    @Override
    public List<AnimalDTO> getAllAnimals() {
        log.info("Fetching all animals");
        List<Animal> animalEntities = animalRepository.findAll();
        return animalEntities.stream()
                .map(animalEntity -> modelMapper.map(animalEntity, AnimalDTO.class))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @Override
    public Response deleteAnimal(int id) {
        log.info("Deleting animal with id: {}", id);
        animalRepository.deleteById(id);
        return new Response("succes", "Deleted animal with id: " + id);
    }

    @Override
    public AnimalDTO updateAnimal(int id, Animal animal) {
        log.info("Updating animal with id: {}", id);
        animal.setId(id);
        animalRepository.save(animal);
        return modelMapper.map(animalRepository.findOneById(id), AnimalDTO.class);
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
        log.info("Fetching image for animal with id: {}", animalId);
        return ImageUtils.decompressImage(animal.getImageData());
    }

    @Override
    public Response adoptAnimal(int animalId, int ownerId) {
        Animal animal = animalRepository.findOneById(animalId);
        animal.setForAdoption(false);
        animal.setOwnerId(ownerId);
        log.info("Animal with id: {} is now adopted", animalId);
        animalRepository.save(animal);
        return new Response("success", "Animal adopted successfully");
    }

    @Override
    public Response abandonAnimal(int animalId) {
        Animal animal = animalRepository.findOneById(animalId);
        animal.setOwnerId(0);
        animal.setForAdoption(true);
        log.info("Animal with id: {} is now available for adoption", animalId);
        animalRepository.save(animal);
        return new Response("success", "Animal abandoned successfully");
    }


    private static String generateAnimalCode() {
        // Increment the counter and format the animal code
        animalCodeCounter++;
        return "AP" + String.format("%05d", animalCodeCounter);
    }

}
