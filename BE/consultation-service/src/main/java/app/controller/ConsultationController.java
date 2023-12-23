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

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/${services.consultation.name}")
@Slf4j
public class ConsultationController {

    private final ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultationDTO> getAllConsultations() {
        return consultationService.getAllConsultations();
    }

    @GetMapping("/download-excel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> exportToExcel() throws IOException {
        byte[] excelDocument = consultationService.generateExcelReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = "Consultations_" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()) + ".xlsx";
        headers.setContentDispositionFormData("attachment", fileName);
        log.info("Exporting all consultations to Excel");
        return new ResponseEntity<>(excelDocument, headers, HttpStatus.OK);
    }

    @GetMapping("/download-word")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<byte[]> exportToWord() throws IOException {
        byte[] wordDocument = consultationService.generateWordReport();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = "Consultations_" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()) + ".docx";
        headers.setContentDispositionFormData("attachment", fileName);
        log.info("Exporting all consultations to Word");

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
    @ResponseStatus(HttpStatus.CREATED)
    public Consultation addConsultation(@RequestBody ConsultationDTO consultationDTO) {
        return consultationService.addConsultation(consultationDTO);
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
    @ResponseStatus(HttpStatus.OK)
    public void deleteConsultation(@PathVariable int id) {
       consultationService.deleteConsultation(id);
    }
}
