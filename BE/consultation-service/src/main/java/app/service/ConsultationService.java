package app.service;

import app.dto.ConsultationDTO;
import app.dto.Response;
import app.entity.Consultation;

import java.util.List;

public interface ConsultationService {

    List<ConsultationDTO> getAllConsultations();

    Consultation getConsultationById(int id);

    Response addConsultation(ConsultationDTO consultation);

    Consultation updateConsultation(int id, Consultation updatedConsultation);

    void deleteConsultation(int id);

    byte[] generateExcelReport();

    byte[] generateWordReport();
}
