package app.service;

import app.dto.DoctorDTO;
import app.dto.ResponseDTO;
import app.entity.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorService {

    List<Doctor> getDoctors();

    Doctor addDoctor(DoctorDTO doctor);

    ResponseDTO deleteDoctor(int id);

    void updateDoctor(DoctorDTO doctorDTO, int id);

    Doctor getDoctorById(int id);

    Doctor getDoctorByLastName(String lastName);

    int getNumberOfAppointments(int id);

    List<String> getDoctorsLastName();
}
