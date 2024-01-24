package app.controller;

import app.entity.Adoption;
import app.service.AdoptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/adoption-service")
public class AdoptionController {

    private final AdoptionService adoptionService;

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<Adoption> getAllAdoptions() {
        return adoptionService.getAllAdoptions();
    }

}
