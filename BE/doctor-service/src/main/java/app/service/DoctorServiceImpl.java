package app.service;

import app.dto.DoctorDTO;
import app.dto.UserDTO;
import app.entity.Doctor;
import app.feign.UserInterface;
import app.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    UserInterface userInterface;

    public DoctorServiceImpl(DoctorRepository doctorRepository, UserInterface userInterface) {
        this.doctorRepository = doctorRepository;
        this.userInterface = userInterface;
    }

    @Override
    public List<Doctor> getDoctors() {
        log.info("Fetching all doctors");
        return doctorRepository.findAll();
    }

    @Override
    public Doctor addDoctor(DoctorDTO doctorDTO) {
        log.info("Creating a doctor!");
        int userId = userInterface.createUser(doctorDTO.getUser()).getId();

        Doctor doctor = Doctor.builder()
                .firstName(doctorDTO.getFirstName())
                .lastName(doctorDTO.getLastName())
                .userId(userId)
                .speciality(doctorDTO.getSpeciality())
                .age(doctorDTO.getAge())
                .yearsOfExperience(doctorDTO.getYearsOfExperience())
                .build();

        doctorRepository.save(doctor);
        return doctor;
    }

    @Override
    public void deleteDoctor(int id) {
        log.info("Deleting doctor with id: {}", id);
        doctorRepository.deleteById(id);
    }

    @Override
    public void updateDoctor(DoctorDTO doctorDTO, int id) {
        log.info("Updating doctor with id: {}", id);

        UserDTO user = userInterface.getUser(id);

        Optional<Doctor> toBeSaved = doctorRepository.findById(id);
        if (toBeSaved.isPresent()) {
            Doctor doctorToBeSaved = toBeSaved.get();
            doctorToBeSaved.setFirstName(doctorDTO.getFirstName());
            doctorToBeSaved.setLastName(doctorDTO.getLastName());
            doctorToBeSaved.setUserId(user.getId());
            doctorToBeSaved.setSpeciality(doctorDTO.getSpeciality());
            doctorToBeSaved.setAge(doctorDTO.getAge());
            doctorToBeSaved.setYearsOfExperience(doctorDTO.getYearsOfExperience());
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

    @Override
    public int getNumberOfAppointments(int id) {
        log.info("Fetching number of appointments for doctor with id: {}", id);
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.get().getNumberOfAppointments();
    }
}