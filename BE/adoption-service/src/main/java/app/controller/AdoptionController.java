package app.controller;

import app.dto.AdoptionDTO;
import app.dto.Response;
import app.entity.Adoption;
import app.service.AdoptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/adoption-service")
public class AdoptionController {

    private final AdoptionService adoptionService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Adoption> getAllAdoptions() {
        return adoptionService.getAllAdoptions();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Adoption getAdoptionById(@PathVariable int id) {
        return adoptionService.getAdoptionById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Response createAdoption(@RequestBody AdoptionDTO adoption) {
        return adoptionService.createAdoption(adoption);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response updateAdoption(@PathVariable Integer id, @RequestBody Adoption adoption) {
        return adoptionService.updateAdoption(id, adoption);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAdoption(@PathVariable Integer id) {
        adoptionService.deleteAdoption(id);
    }
}
