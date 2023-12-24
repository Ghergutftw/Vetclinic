package app.controller;

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
@RequestMapping("/${services.appointment.name}/owner")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
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
