package app.feign;

import app.dto.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "owner-service", url = "http://localhost:8083/appointment-service/owner/")
public interface OwnerInterface {

    @PostMapping("/abandon/{animalId}")
    Response abandon(@PathVariable int animalId);

}
