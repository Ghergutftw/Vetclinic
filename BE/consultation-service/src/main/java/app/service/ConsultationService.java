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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
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

    public List<ConsultationDTO> getAllConsultations() {
        log.info("Fetching all consultations");
        List<Consultation> allConsultations = consultationRepository.findAll();
        List<ConsultationDTO> consultations = new ArrayList<>();
        allConsultations.forEach(consultation -> {
            DoctorResponse doctor = doctorInterface.getDoctor(consultation.getDoctorId());
            AnimalDTO animal = animalInterface.getAnimalById(consultation.getAnimalId());
            ConsultationDTO consultationDTO = ConsultationDTO.builder()
                    .id(consultation.getId())
                    .date(consultation.getDate())
                    .doctorLastName(doctor.getLastName())
                    .animalCode(animal.getAnimalCode())
                    .consultatedAnimal(animal)
                    .diagnostic(consultation.getDiagnostic())
                    .treatment(consultation.getTreatment())
                    .recommendations(consultation.getRecommendations())
                    .build();
            consultations.add(consultationDTO);
        });

        return consultations;
    }

    public Consultation getConsultationById(int id) {
        log.info("Fetching consultation by ID: {}", id);
        Optional<Consultation> consultation = consultationRepository.findById(id);
        return consultation.orElse(null);
    }

    public Consultation addConsultation(ConsultationDTO consultation) {
        DoctorResponse doctor = doctorInterface.getDoctorByLastName(consultation.getDoctorLastName());

        AnimalDTO animalCreated = animalInterface.addAnimal(consultation.getConsultatedAnimal());
        log.info("Adding new consultation: {}", consultation);

//        Extrage Consultation din ConsultationDTO
        Consultation toAddConsultation = Consultation.builder()
                .date(consultation.getDate())
                .doctorId(doctor.getId())
                .animalId(animalCreated.getId())
                .diagnostic(consultation.getDiagnostic())
                .treatment(consultation.getTreatment())
                .recommendations(consultation.getRecommendations())
                .price(consultation.getPrice())
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

    private static final String EXCEL_TEMPLATE_PATH = "path/to/excel_template.xlsx";
    private static final String WORD_TEMPLATE_PATH = "path/to/word_template.docx";


    public byte[] generateExcelReport() {
        List<Consultation> consultations = consultationRepository.findAll();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Consultation Report");

            // Create the header row
            Row headerRow = sheet.createRow(0);
            createExcelCell(headerRow, 0, "Date");
            createExcelCell(headerRow, 1, "Doctor ID");
            createExcelCell(headerRow, 2, "Animal ID");
            createExcelCell(headerRow, 3, "Diagnostic");
            createExcelCell(headerRow, 4, "Treatment");
            createExcelCell(headerRow, 5, "Recommendations");
            createExcelCell(headerRow, 6, "Price");

            int totalPrice = 0;

            // Populate the sheet with consultation data
            int rowNum = 1;
            for (Consultation consultation : consultations) {
                Row row = sheet.createRow(rowNum++);
                createExcelCell(row, 0, consultation.getDate().toString());
                createExcelCell(row, 1, String.valueOf(consultation.getDoctorId()));
                createExcelCell(row, 2, String.valueOf(consultation.getAnimalId()));
                createExcelCell(row, 3, consultation.getDiagnostic());
                createExcelCell(row, 4, consultation.getTreatment());
                createExcelCell(row, 5, consultation.getRecommendations());
                createExcelCell(row, 6, String.valueOf(consultation.getPrice()));
                totalPrice += consultation.getPrice();
            }

            // Add a row for the total price at the bottom
            Row totalRow = sheet.createRow(rowNum);
            createExcelCell(totalRow, 5, "Total Price");
            createExcelCell(totalRow, 6, String.valueOf(totalPrice));

            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            // Handle exception appropriately
            e.printStackTrace();
            return new byte[0];
        }

    }

    public byte[] generateWordReport() {
        List<Consultation> consultations = consultationRepository.findAll();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             XWPFDocument document = new XWPFDocument()) {

            XWPFParagraph titleParagraph = document.createParagraph();
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("Consultation Report");
            titleRun.setFontSize(16);
            titleRun.setBold(true);

            // Create a table
            XWPFTable table = document.createTable();

            // Create the header row
            XWPFTableRow headerRow = table.getRow(0);
            createCell(headerRow, 0, "Date");
            createCell(headerRow, 1, "Doctor ID");
            createCell(headerRow, 2, "Animal ID");
            createCell(headerRow, 3, "Diagnostic");
            createCell(headerRow, 4, "Treatment");
            createCell(headerRow, 5, "Recommendations");
            createCell(headerRow, 6, "Price");

            int totalPrice = 0;

            // Populate the table with consultation data
            for (Consultation consultation : consultations) {
                XWPFTableRow row = table.createRow();
                createCell(row, 0, consultation.getDate().toString());
                createCell(row, 1, String.valueOf(consultation.getDoctorId()));
                createCell(row, 2, String.valueOf(consultation.getAnimalId()));
                createCell(row, 3, consultation.getDiagnostic());
                createCell(row, 4, consultation.getTreatment());
                createCell(row, 5, consultation.getRecommendations());
                createCell(row, 6, String.valueOf(consultation.getPrice()));
                totalPrice += consultation.getPrice();
            }

            // Add a row for the total price at the bottom
            XWPFTableRow totalRow = table.createRow();
            createCell(totalRow, 5, "Total Price");
            createCell(totalRow, 6, String.valueOf(totalPrice));

            // Auto-size columns
            setColumnWidths(table);

            document.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            // Handle exception appropriately
            e.printStackTrace();
            return new byte[0];
        }
    }

    private void createCell(XWPFTableRow row, int cellIndex, String text) {
        XWPFTableCell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell();
        }
        cell.setText(text);
    }

    private void createExcelCell(Row row, int cellIndex, String text) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(text);
    }

    private void setColumnWidths(XWPFTable table) {
        int numColumns = table.getRow(0).getTableCells().size();

        // Set a default width or adjust based on your estimation
        int defaultColumnWidth = 2000; // Set to a value that suits your needs

        for (int col = 0; col < numColumns; col++) {
            table.getRow(0).getCell(col).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(defaultColumnWidth));
        }
    }
}
