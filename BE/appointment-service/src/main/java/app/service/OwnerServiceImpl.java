package app.service;

import app.dto.AdoptionDTO;
import app.dto.Response;
import app.dto.UserDTO;
import app.entity.Owner;
import app.feign.UserInterface;
import app.repository.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    UserInterface userInterface;

    public OwnerServiceImpl(OwnerRepository ownerRepository, UserInterface userInterface) {
        this.ownerRepository = ownerRepository;
        this.userInterface = userInterface;
    }


    @Override
    public List<Owner> getAllOwners() {
        log.info("Fetching all owners from the database.");
        return ownerRepository.findAll();
    }

    @Override
    public Owner getOwnerById(int ownerId) {
        log.info("Fetching owner with ID: {}", ownerId);
        Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        return optionalOwner.orElse(null);
    }

    @Override
    public Owner addOwner(Owner owner) {
        log.info("Adding a new owner to the database: {}", owner);
        ownerRepository.save(owner);
        log.info("Owner added successfully.");
        return owner;
    }

    @Override
    public Owner updateOwner(int id, Owner owner) {
        log.info("Updating owner with id: {}", id);
        owner.setId(id);
        ownerRepository.save(owner);
        return ownerRepository.findOneById(id);
    }

    @Override
    public Response deleteOwner(int ownerId) {
        log.info("Deleting owner with ID: {}", ownerId);
        ownerRepository.deleteById(ownerId);
        log.info("Owner deleted successfully.");
        return new Response("success","Owner deleted successfully." );
    }

    @Override
    public int adopt(AdoptionDTO adoption) {
        UserDTO user = userInterface.getUser(adoption.getUsername());
        Owner oneByEmail = ownerRepository.findOneByEmail(user.getEmail());
        if (oneByEmail == null){
            Owner built = Owner.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .userId(user.getId())
                    .build();
            ownerRepository.save(built);
            log.info("Owner with id : {} added successfully." , built.getId());
            return built.getId();
        }else {
            List<Integer> ownedAnimals = oneByEmail.getOwnedAnimals();
            ownedAnimals.add(adoption.getAnimalId());
            oneByEmail.setOwnedAnimals(ownedAnimals);
            ownerRepository.save(oneByEmail);
            log.info("Owner with id : {} updated successfully." , oneByEmail.getId());
            return oneByEmail.getId();
        }
    }

    @Override
    public int abandon(AdoptionDTO adoption) {
        UserDTO user = userInterface.getUser(adoption.getUsername());
        Owner oneByEmail = ownerRepository.findOneByEmail(user.getEmail());
        if (oneByEmail == null){
            log.info("Owner with id : {} not found." , oneByEmail.getId());
            return -1;
        }else {
            log.info("Owner with id : {} is abandoning." , oneByEmail.getId());
            List<Integer> ownedAnimals = oneByEmail.getOwnedAnimals();
            ownedAnimals.removeIf(integer -> integer == adoption.getAnimalId());
            oneByEmail.setOwnedAnimals(ownedAnimals);
            ownerRepository.save(oneByEmail);
            log.info("Owner with id : {} updated successfully." , oneByEmail.getId());
            return oneByEmail.getId();
        }
    }
}
