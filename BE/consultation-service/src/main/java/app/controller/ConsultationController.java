package app.controller;

import app.dto.ConsultationDTO;
import app.dto.Response;
import app.entity.Consultation;
import app.enums.Diagnosis;
import app.enums.Recommendation;
import app.enums.Treatment;
import app.service.ConsultationService;
import app.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/consultation-service")
@Slf4j
@AllArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    private final EmailService emailService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ConsultationDTO> getAllConsultations() {
        return consultationService.getAllConsultations();
    }

    @GetMapping("/download-excel")
    public ResponseEntity<byte[]> exportToExcel() {
        byte[] excelDocument = consultationService.generateExcelReport();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = "Consultations_" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()) + ".xlsx";
        headers.setContentDispositionFormData("attachment", fileName);
        log.info("Exporting all consultations to Excel");
        return new ResponseEntity<>(excelDocument, headers, HttpStatus.ACCEPTED);
    }

    @GetMapping("/download-word")
    public ResponseEntity<byte[]> exportToWord() {
        byte[] wordDocument = consultationService.generateWordReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = "Consultations_" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()) + ".docx";
        headers.setContentDispositionFormData("attachment", fileName);
        log.info("Exporting all consultations to Word");

        return new ResponseEntity<>(wordDocument, headers, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable int id) {
        Consultation consultation = consultationService.getConsultationById(id);
        if (consultation != null) {
            return new ResponseEntity<>(consultation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Response addConsultation(@RequestBody ConsultationDTO consultation ) {
        return consultationService.addConsultation(consultation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultation> updateConsultation(@PathVariable int id, @RequestBody Consultation consultation) {
        Consultation updatedConsultation = consultationService.updateConsultation(id, consultation);
        if (updatedConsultation != null) {
            return new ResponseEntity<>(updatedConsultation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/get-receipt")
    @ResponseStatus(HttpStatus.OK)
    public Response sendEmailWithAttachment(@RequestParam String email , @RequestBody ConsultationDTO consultationDTO) throws MessagingException, IOException {
        return emailService.getReceipt(email,consultationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteConsultation(@PathVariable int id) {
        consultationService.deleteConsultation(id);
    }

    @GetMapping("/diagnoses")
    public String[] getAnimalDiseases() {
        return Diagnosis.getAllDisplayNames();
    }

    @GetMapping("/recommendations")
    public String[] getRecommendations() {
        return Recommendation.getAllDisplayNames();
    }

    @GetMapping("/treatments")
    public String[] getTreatments() {
        return Treatment.getAllDisplayNames();
    }
}
