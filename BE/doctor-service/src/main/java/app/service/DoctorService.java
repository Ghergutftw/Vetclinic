package app.service;

import app.entity.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface DoctorService {

    List<Doctor> getDoctors();

    Doctor addDoctor(Doctor doctor);

    void deleteDoctor(int id);

    void updateDoctor(Doctor doctor, int id);

    Doctor getDoctorById(int id);
}
