package app.service;

import app.dto.Response;
import app.entity.Owner;

import java.util.List;

public interface OwnerService {

    List<Owner> getAllOwners();

    Owner getOwnerById(int ownerId);

    Owner addOwner(Owner owner);

    Owner updateOwner(int id, Owner owner);

    Response deleteOwner(int ownerId);
}
