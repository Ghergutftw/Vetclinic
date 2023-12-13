package app.service;

import app.dto.AnimalDTO;
import app.dto.ConsultationDTO;
import app.dto.DoctorResponse;
import app.feign.AnimalInterface;
import app.entity.Consultation;
import app.feign.DoctorInterface;
import app.repository.ConsultationRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ConsultationService {

    private final ConsultationRepository consultationRepository;

    DoctorInterface doctorInterface;

    AnimalInterface animalInterface;

    public ConsultationService(ConsultationRepository consultationRepository, DoctorInterface doctorInterface, AnimalInterface animalInterface) {
        this.consultationRepository = consultationRepository;
        this.doctorInterface = doctorInterface;
        this.animalInterface = animalInterface;
    }

    public List<Consultation> getAllConsultations() {
        log.info("Fetching all consultations");
        return consultationRepository.findAll();
    }

    public Consultation getConsultationById(int id) {
        log.info("Fetching consultation by ID: {}", id);
        Optional<Consultation> consultation = consultationRepository.findById(id);
        return consultation.orElse(null);
    }

    public Consultation addConsultation(ConsultationDTO consultation) {
        DoctorResponse doctor = doctorInterface.getDoctorByLastName(consultation.getDoctorLastName());

        AnimalDTO animalCreated = animalInterface.addAnimal(consultation.getAnimal());
        log.info("Adding new consultation: {}", consultation.getId());

//        Extrage Consultation din ConsultationDTO
        Consultation toAddConsultation = Consultation.builder()
                .date(consultation.getDate())
                .doctorId(doctor.getId())
                .animalId(animalCreated.getId())
                .diagnostic(consultation.getDiagnostic())
                .treatment(consultation.getTreatment())
                .recommendations(consultation.getRecommendations())
                .build();
        return consultationRepository.save(toAddConsultation);
    }

    public Consultation updateConsultation(int id, Consultation updatedConsultation) {
        log.info("Updating consultation with ID {}: {}", id, updatedConsultation);

        Consultation existingConsultation = consultationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Consultation with ID " + id + " not found"));

        Consultation updatedConsultationEntity = Consultation.builder()
                .id(existingConsultation.getId())
                .date(updatedConsultation.getDate())
                .doctorId(updatedConsultation.getDoctorId())
                .animalId(updatedConsultation.getAnimalId())
                .diagnostic(updatedConsultation.getDiagnostic())
                .treatment(updatedConsultation.getTreatment())
                .recommendations(updatedConsultation.getRecommendations())
                .build();

        log.info("Consultation updated successfully");
        return consultationRepository.save(updatedConsultationEntity);
    }

    public void deleteConsultation(int id) {
        log.info("Deleting consultation with ID: {}", id);
        consultationRepository.deleteById(id);
    }

}
