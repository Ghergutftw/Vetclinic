package app.controller;

import app.dto.AdoptionDTO;
import app.dto.Response;
import app.entity.Owner;
import app.service.OwnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/appointment-service/owner")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @PostMapping("/adopt")
    @ResponseStatus(HttpStatus.OK)
    public int adopt(@RequestBody AdoptionDTO adoption) {
        return ownerService.adopt(adoption);
    }

    @PostMapping("/abandon/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public Response abandon(@PathVariable int animalId) {
        return ownerService.abandon(animalId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Owner addOwner(@RequestBody Owner owner) {
        return ownerService.addOwner(owner);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteOwner(@PathVariable int id) {
        return ownerService.deleteOwner(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Owner updateOwner(@PathVariable int id, @RequestBody Owner owner) {
        return ownerService.updateOwner(id, owner);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Owner getOwnerById(@PathVariable int id) {
        return ownerService.getOwnerById(id);
    }
}
