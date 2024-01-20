package app.components;

import app.entity.Doctor;
import app.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DataLoader(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void run(String... args) {
        Doctor doctor = new Doctor(0,"Madalin", "Ghergut", 0 , "Cardiologie", 25, 5, 0);
        doctorRepository.save(doctor);
    }
}
