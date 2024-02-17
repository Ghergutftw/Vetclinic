package app.feign;

import app.dto.AnimalDTO;
import app.dto.ConsultationCreation;
import app.dto.Creation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "animal-service", url = "http://localhost:8081/animal-service")
public interface AnimalInterface {

    @PostMapping("")
    AnimalDTO addAnimal(@RequestBody AnimalDTO animal);

    @GetMapping("/{id}")
    AnimalDTO getAnimalById(@PathVariable int id);

    @PostMapping("/add-from-consultation")
    Creation addAnimalFromConsultation(@RequestBody ConsultationCreation creation);


}
