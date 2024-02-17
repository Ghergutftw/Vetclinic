package app.service;

import app.dto.AdoptionDTO;
import app.dto.Response;
import app.entity.Adoption;
import app.feign.AnimalInterface;
import app.feign.OwnerInterface;
import app.repository.AdoptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdoptionServiceImpl implements AdoptionService {

    private final AdoptionRepository adoptionRepository;

    AnimalInterface animalInterface;
    OwnerInterface ownerInterface;

    public AdoptionServiceImpl(AdoptionRepository adoptionRepository, AnimalInterface animalInterface, OwnerInterface ownerInterface) {
        this.adoptionRepository = adoptionRepository;
        this.animalInterface = animalInterface;
        this.ownerInterface = ownerInterface;
    }

    @Override
    public List<Adoption> getAllAdoptions() {
        log.info("Fetching all adoptions");
        return adoptionRepository.findAll();
    }

    @Override
    public Adoption getAdoptionById(Integer id) {
        log.info("Fetching adoption with id: {}", id);
        Optional<Adoption> adoption = adoptionRepository.findById(id);
        return adoption.orElse(null);
    }

    @Override
    public Response createAdoption(AdoptionDTO adoption) {
        log.info("Creating adoption");
        AdoptionDTO adoptionDTO = new AdoptionDTO();
        adoptionDTO.setEmail(adoption.getEmail());
        adoptionDTO.setAnimalCode(adoption.getAnimalCode());
        adoptionDTO.setAdoptionDate(adoption.getAdoptionDate());
        int ownerId = ownerInterface.adopt(adoptionDTO);
        Response animalResponse = animalInterface.adopt(adoption.getAnimalCode() , ownerId);

        if (!animalResponse.getStatus().equals("success")) {
            log.error("Error creating adoption: {}", animalResponse.getMessage());
        }

        Adoption toBeAdded = new Adoption();
        toBeAdded.setAnimalCode(adoption.getAnimalCode());
        toBeAdded.setDate(adoption.getAdoptionDate());
        toBeAdded.setOwnerId(ownerId);

        adoptionRepository.save(toBeAdded);

        return new Response("succes", "Adoption created");
    }

    @Override
    public Response updateAdoption(Integer id, Adoption updatedAdoption) {
        log.info("Updating adoption with id: {}", id);
        Optional<Adoption> existingAdoption = adoptionRepository.findById(id);

        if (existingAdoption.isPresent()) {
            Adoption adoption = existingAdoption.get();
            adoption.setAnimalCode(updatedAdoption.getAnimalCode());
            adoption.setOwnerId(updatedAdoption.getOwnerId());
            adoption.setDate(updatedAdoption.getDate());
            adoptionRepository.save(adoption);
            return new Response("success", "Adoption updated");
        }
        return new Response("error", "Adoption not found");
    }

    @Override
    public void deleteAdoption(Integer id) {
        log.info("Deleting adoption with id: {}", id);
        adoptionRepository.deleteById(id);
    }
}

