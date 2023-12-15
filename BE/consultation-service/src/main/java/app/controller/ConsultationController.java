package app.controller;

import app.dto.ConsultationDTO;
import app.entity.Consultation;
import app.service.ConsultationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.apache.tomcat.util.http.FastHttpDateFormat.getCurrentDate;

@RestController
@RequestMapping("/consultation-service")
@Slf4j
public class ConsultationController {

    private final ConsultationService consultationService;


    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ConsultationDTO>> getAllConsultations() {
        List<ConsultationDTO> consultations = consultationService.getAllConsultations();
        return new ResponseEntity<>(consultations, HttpStatus.OK);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportToWord() {
        byte[] wordDocument = consultationService.generateWordReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = "Consultation_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".docx";
        headers.setContentDispositionFormData("attachment", fileName);

        return new ResponseEntity<>(wordDocument, headers, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Consultation> getConsultationById(@PathVariable int id) {
        Consultation consultation = consultationService.getConsultationById(id);
        if (consultation != null) {
            return new ResponseEntity<>(consultation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Consultation> addConsultation(@RequestBody ConsultationDTO consultationDTO) {
        Consultation addedConsultation = consultationService.addConsultation(consultationDTO);
        return new ResponseEntity<>(addedConsultation, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Consultation> updateConsultation(@PathVariable int id, @RequestBody Consultation consultation) {
        Consultation updatedConsultation = consultationService.updateConsultation(id, consultation);
        if (updatedConsultation != null) {
            return new ResponseEntity<>(updatedConsultation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable int id) {
        consultationService.deleteConsultation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
