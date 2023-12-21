package app.feign;

import app.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${services.doctor.name}" , url = "${services.doctor.url}" )
public interface DoctorInterface{

    @GetMapping("/get/{id}")
    DoctorResponse getDoctor(@PathVariable int id);

    @GetMapping("/get-by-last-name/{lastName}")
    DoctorResponse getDoctorByLastName(@PathVariable String lastName);

}
