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
        Owner savedOwner = ownerRepository.save(owner);
        log.info("Owner added successfully.");
        return savedOwner;
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
        return new Response("success", "Owner deleted successfully.");
    }

    @Override
    public int adopt(AdoptionDTO adoption) {
        UserDTO user;
//        TODO :Merge momentan ? fa user cand e trimis din consultate si trimite email cu informatiile create
//        OwnerDTO ownerToBeUsered = adoption.getOwner();
//        ownerToBeUsered.setUsername(adoption.getOwner().getFirstName().toLowerCase()+"."+adoption.getOwner().getFirstName().toLowerCase());
//        ownerToBeUsered.setPassword(adoption.getOwner().getFirstName()+adoption.getOwner().getLastName());
//        userInterface.saveUser(ownerToBeUsered);
        user = userInterface.getUserByEmail(adoption.getEmail());

        Owner owner = ownerRepository.findOneByEmail(user.getEmail());
        if (owner == null) {
            Owner built = Owner.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .userId(user.getId())
                    .ownedAnimals(List.of(adoption.getAnimalCode()))
                    .build();
            ownerRepository.save(built);
            log.info("Owner with id : {} added successfully.", built.getId());
            return built.getId();
        } else {
            List<String> ownedAnimals = owner.getOwnedAnimals();
            ownedAnimals.add(adoption.getAnimalCode());
            owner.setOwnedAnimals(ownedAnimals);
            ownerRepository.save(owner);
            log.info("Owner with id : {} updated successfully.", owner.getId());
            return owner.getId();
        }
    }

    @Override
    public Response abandon(String animalCode) {
        List<Owner> all = ownerRepository.findAll();
        for (Owner owner : all) {
            List<String> ownedAnimals = owner.getOwnedAnimals();
            if (ownedAnimals.contains(animalCode)) {
                ownedAnimals.remove(String.valueOf(animalCode));
                owner.setOwnedAnimals(ownedAnimals);
                ownerRepository.save(owner);
                log.info("Animal with id : {} abandoned successfully.", animalCode);
                return new Response("success", "Animal abandoned successfully.");
            }
        }
        return new Response("failed", "Something went wrong.");
    }

    @Override
    public Owner getOwnerByEmail(String email) {
        return ownerRepository.findOneByEmail(email);
    }

//    @Override
//    public Owner getOwnerByUsername(String username) {
//        return ownerRepository.findOneByUsername(username);
//    }

}
