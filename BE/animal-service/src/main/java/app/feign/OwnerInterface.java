package app.feign;

import app.dto.AdoptionDTO;
import app.dto.OwnerDTO;
import app.dto.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "owner-service", url = "http://localhost:8083/appointment-service/owner/")
public interface OwnerInterface {

    @PostMapping("/abandon")
    Response abandon(@RequestParam String animalCode);

    @GetMapping("/owners/email")
    OwnerDTO getOwnerByEmail(@RequestParam String email);

    @PostMapping("")
    OwnerDTO createOwner(@RequestBody OwnerDTO ownerDTO);

    @PostMapping("/adopt")
    int adopt(@RequestBody AdoptionDTO adoption);


}
