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
@RequestMapping("/${services.doctor.name}")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<Doctor> getDoctors(){
        return doctorService.getDoctors();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor addDoctor(@RequestBody Doctor doctor){
       return doctorService.addDoctor(doctor);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteDoctor(@PathVariable int id){
        doctorService.deleteDoctor(id);
    }

    @PutMapping( "/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateDoctor(@RequestBody Doctor doctor,@PathVariable int id) {
        doctorService.updateDoctor(doctor,id);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Doctor getDoctor(@PathVariable int id){
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/get-by-last-name/{lastName}")
    @ResponseStatus(HttpStatus.OK)
    public Doctor getDoctorByLastName(@PathVariable String lastName){
        return doctorService.getDoctorByLastName(lastName);
    }
}
