package app.feign;

import app.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DOCTOR-SERVICE" , url = "http://localhost:8081/doctor-service" )
public interface DoctorInterface{

    @GetMapping("/get/{id}")
    DoctorResponse getDoctor(@PathVariable int id);

    @GetMapping("/get-by-last-name/{lastName}")
    DoctorResponse getDoctorByLastName(@PathVariable String lastName);

}
