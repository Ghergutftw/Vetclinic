package app.service;

import app.entity.Doctor;
import app.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public List<Doctor> getDoctors() {
        log.info("Fetching all doctors");
        return doctorRepository.findAll();
    }

    @Override
    public Doctor addDoctor(Doctor doctor) {
        log.info("Adding doctor");
        doctorRepository.save(doctor);
        return doctor;
    }

    @Override
    public void deleteDoctor(int id) {
        log.info("Deleting doctor with id: {}", id);
        doctorRepository.deleteById(id);
    }

    @Override
    public void updateDoctor(Doctor doctor, int id) {
        log.info("Updating doctor with id: {}", id);
        Optional<Doctor> toBeSaved = doctorRepository.findById(id);
        if (toBeSaved.isPresent()) {
            Doctor doctorToBeSaved = toBeSaved.get();
            doctorToBeSaved.setFirstName(doctor.getFirstName());
            doctorToBeSaved.setLastName(doctor.getLastName());
            doctorToBeSaved.setUser(doctor.getUser());
            doctorToBeSaved.setSpeciality(doctor.getSpeciality());
            doctorToBeSaved.setAge(doctor.getAge());
            doctorToBeSaved.setYearsOfExperience(doctor.getYearsOfExperience());
            doctorRepository.save(doctorToBeSaved);
        }
    }

    @Override
    public Doctor getDoctorById(int id) {
        log.info("Fetching doctor with id: {}", id);
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElse(null);
    }

    @Override
    public Doctor getDoctorByLastName(String lastName) {
        log.info("Fetching doctor with last name: {}", lastName);
        Optional<Doctor> doctor = doctorRepository.findByLastName(lastName);
        return doctor.orElse(null);
    }
}