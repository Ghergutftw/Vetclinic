package app.feign;

import app.dto.OwnerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "owner-service", url = "http://localhost:8083/appointment-service/owner/")
public interface OwnerInterface {

    @GetMapping("/owners/email")
    OwnerDTO getOwnerByEmail(@RequestParam String email);
}