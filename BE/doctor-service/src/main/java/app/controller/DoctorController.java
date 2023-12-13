package app.controller;

import app.entity.Doctor;
import app.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/doctor-service")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/get-all")
    public List<Doctor> getDoctors(){
        return doctorService.getDoctors();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor addDoctor(@RequestBody Doctor doctor){
       return doctorService.addDoctor(doctor);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDoctor(@PathVariable int id){
        doctorService.deleteDoctor(id);
    }

    @PutMapping( "/update/{id}")
    public void updateDoctor(@RequestBody Doctor doctor,@PathVariable int id) {
        doctorService.updateDoctor(doctor,id);
    }

    @GetMapping("/get/{id}")
    public Doctor getDoctor(@PathVariable int id){
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/get-by-last-name/{lastName}")
    public Doctor getDoctorByLastName(@PathVariable String lastName){
        return doctorService.getDoctorByLastName(lastName);
    }
}
