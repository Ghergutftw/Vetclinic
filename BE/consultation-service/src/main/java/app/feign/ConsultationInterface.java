package app.feign;

import app.DTO.DoctorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DOCTOR-SERVICE" , url = "http://localhost:8081/doctor-service" )
public interface ConsultationInterface {

    @GetMapping("/get/{id}")
    DoctorDTO getDoctor(@PathVariable int id);

}
