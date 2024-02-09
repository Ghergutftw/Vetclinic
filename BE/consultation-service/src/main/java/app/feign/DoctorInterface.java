package app.feign;

import app.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "doctor-service" , url = "http://localhost:8084/doctor-service" )
public interface DoctorInterface{

    @GetMapping("/{id}")
    DoctorResponse getDoctor(@PathVariable int id);

    @GetMapping("/doctors")
    DoctorResponse getDoctorByLastName(@RequestParam String lastName);


}
