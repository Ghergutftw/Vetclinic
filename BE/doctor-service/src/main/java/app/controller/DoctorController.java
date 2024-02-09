package app.controller;

import app.dto.DoctorDTO;
import app.dto.ResponseDTO;
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

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<Doctor> getDoctors(){
        return doctorService.getDoctors();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor addDoctor(@RequestBody DoctorDTO doctor){
       return doctorService.addDoctor(doctor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseDTO deleteDoctor(@PathVariable int id) {
        return doctorService.deleteDoctor(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateDoctor(@RequestBody DoctorDTO doctorDTO,@PathVariable int id) {
        doctorService.updateDoctor(doctorDTO,id);
    }

    @GetMapping("/last-names")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getDoctorsLastName(){
        return doctorService.getDoctorsLastName();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Doctor getDoctor(@PathVariable int id){
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/doctors")
    @ResponseStatus(HttpStatus.OK)
    public Doctor getDoctorByLastName(@RequestParam String lastName) {
        return doctorService.getDoctorByLastName(lastName);
    }

    //    TODO : What is the purpose of this method? To tired to check it now ¯\_(ツ)_/¯
    @PostMapping("/add-appointment/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public int addConsultation(@PathVariable int id){
        return doctorService.getNumberOfAppointments(id);
    }

}
