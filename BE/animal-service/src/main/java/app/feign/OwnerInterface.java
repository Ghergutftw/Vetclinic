package app.feign;

import app.dto.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "owner-service", url = "http://localhost:8083/appointment-service/owner/")
public interface OwnerInterface {

    @PostMapping("/abandon")
    Response abandon(@RequestParam int id);

}
