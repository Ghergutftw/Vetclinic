package app.service;

import app.dto.AdoptionDTO;
import app.dto.Response;
import app.entity.Owner;

import java.util.List;

public interface OwnerService {

    List<Owner> getAllOwners();

    Owner getOwnerById(int ownerId);

    Owner addOwner(Owner owner);

    Owner updateOwner(int id, Owner owner);

    Response deleteOwner(int ownerId);

    int adopt(AdoptionDTO adoption);

    Response abandon(String animalCode);

    Owner getOwnerByEmail(String email);
}
