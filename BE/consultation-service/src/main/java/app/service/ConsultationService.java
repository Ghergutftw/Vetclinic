package app.service;

import app.entity.Consultation;
import app.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;

    public List<Consultation> getAllConsultations() {
        log.info("Fetching all consultations");
        return consultationRepository.findAll();
    }

    public Consultation getConsultationById(int id) {
        log.info("Fetching consultation by ID: {}", id);
        Optional<Consultation> consultation = consultationRepository.findById(id);
        return consultation.orElse(null);
    }

    public Consultation addConsultation(Consultation consultation) {
        log.info("Adding new consultation: {}", consultation);
        return consultationRepository.save(consultation);
    }

    public Consultation updateConsultation(int id, Consultation updatedConsultation) {
        log.info("Updating consultation with ID {}: {}", id, updatedConsultation);
        Optional<Consultation> existingConsultationOptional = consultationRepository.findById(id);
        if (existingConsultationOptional.isPresent()) {
            Consultation existingConsultation = existingConsultationOptional.get();
            existingConsultation.setDate(updatedConsultation.getDate());
            existingConsultation.setDoctorId(updatedConsultation.getDoctorId());
            existingConsultation.setAnimalId(updatedConsultation.getAnimalId());
            existingConsultation.setDiagnostic(updatedConsultation.getDiagnostic());
            existingConsultation.setTreatment(updatedConsultation.getTreatment());
            existingConsultation.setRecommendations(updatedConsultation.getRecommendations());
            existingConsultation.setStatus(updatedConsultation.getStatus());
            log.info("Consultation updated successfully");
            return consultationRepository.save(existingConsultationOptional.get());
        } else {
            log.warn("Consultation with ID {} not found", id);
            return null; // Handle not found case
        }
    }

    public void deleteConsultation(int id) {
        log.info("Deleting consultation with ID: {}", id);
        consultationRepository.deleteById(id);
    }
}
