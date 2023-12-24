package app.service;

import app.dto.Response;
import app.entity.Owner;
import app.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;


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
        log.info("Updating owner with ID: {}", owner.getId());
        ownerRepository.save(owner);
        log.info("Owner updated successfully.");
        return owner;
    }

    @Override
    public Response deleteOwner(int ownerId) {
        log.info("Deleting owner with ID: {}", ownerId);
        ownerRepository.deleteById(ownerId);
        log.info("Owner deleted successfully.");
        return new Response("success","Owner deleted successfully." );
    }
}
