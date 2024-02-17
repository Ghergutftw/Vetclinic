package app.feign;

import app.dto.OwnerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "owner-service", url = "http://localhost:8083/appointment-service/owner/")
public interface OwnerInterface {

    @GetMapping("/{id}")
    OwnerDTO getOwnerById(@PathVariable int id);

}
