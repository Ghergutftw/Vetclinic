package app.service;

import app.dto.*;
import app.entity.Animal;
import app.feign.OwnerInterface;
import app.repository.AnimalRepository;
import app.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    OwnerInterface ownerInterface;

    public AnimalServiceImpl(AnimalRepository animalRepository, OwnerInterface ownerInterface) {
        this.animalRepository = animalRepository;
        this.ownerInterface = ownerInterface;
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
    public byte[] getImage(String animalCode) throws IOException {
        Animal animal = animalRepository.findOneByAnimalCode(animalCode);
        log.info("Fetching image for animal with animal code: {}", animalCode);
        return ImageUtils.decompressImage(animal.getImageData());
    }

    @Override
    public Response adoptAnimal(String animalCode, int ownerId) {
        Animal animal = animalRepository.findOneByAnimalCode(animalCode);
        animal.setForAdoption(false);
        animal.setOwnerId(ownerId);
        log.info("Animal with id: {} is now adopted", animalCode);
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
        ownerInterface.abandon(animal.getAnimalCode());
        return new Response("success", "Animal abandoned successfully");
    }

    @Override
    public Animal getAnimalByCode(String animalCode) {
        log.info("Fetching animal with code: {}", animalCode);
        return animalRepository.findOneByAnimalCode(animalCode);
    }

    @Override
    public Creation addAnimalFromConsultation(ConsultationCreation consultationCreation) {
        Creation creation = new Creation();
        OwnerDTO owner = ownerInterface.getOwnerByEmail(consultationCreation.getOwnerDTO().getEmail());
        AnimalDTO animalDTO = consultationCreation.getAnimalDTO();
        Animal animal = animalRepository.findAnimalByNicknameAndOwnerIdAndAnimalTypeAndSpecie(animalDTO.getNickname(), owner.getId(),animalDTO.getAnimalType(),animalDTO.getSpecie());

        if (animal == null) {
            Animal animalToBeSaved = Animal.builder()
                    .nickname(consultationCreation.getAnimalDTO().getNickname())
                    .animalType(consultationCreation.getAnimalDTO().getAnimalType())
                    .animalCode(generateAnimalCode())
                    .specie(consultationCreation.getAnimalDTO().getSpecie())
                    .age(consultationCreation.getAnimalDTO().getAge())
                    .weight(consultationCreation.getAnimalDTO().getWeight())
                    .ownerId(owner.getId())
                    .consultations(1)
                    .forAdoption(false).build();
            Animal animalSaved = animalRepository.save(animalToBeSaved);
            AdoptionDTO adoptionDTO = new AdoptionDTO();
            adoptionDTO.setEmail(owner.getEmail());
            adoptionDTO.setOwner(consultationCreation.getOwnerDTO());
            adoptionDTO.setAnimalCode(animalSaved.getAnimalCode());
            ownerInterface.adopt(adoptionDTO);
            creation.setOwnerId(owner.getId());
            creation.setAnimalId(animalSaved.getId());
        } else if (owner.getOwnedAnimals().contains(animal.getAnimalCode())) {
            animal.setConsultations(animal.getConsultations() + 1);
            animalRepository.save(animal);
            creation.setOwnerId(owner.getId());
            creation.setAnimalId(animal.getId());
        }
        return creation;
    }


    private static String generateAnimalCode() {
        animalCodeCounter++;
        return "AP" + String.format("%05d", animalCodeCounter);
    }

}
