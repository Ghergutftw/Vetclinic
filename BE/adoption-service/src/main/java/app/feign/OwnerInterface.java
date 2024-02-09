package app.feign;

import app.dto.AdoptionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "appointment-service", url = "http://localhost:8083/appointment-service/owner")
public interface OwnerInterface {

    @PostMapping("/adopt")
    int adopt(@RequestBody AdoptionDTO adoptionDTO);

}
