package app.service;

import app.dto.AnimalDTO;
import app.dto.ConsultationDTO;
import app.dto.DoctorResponse;
import app.entity.Consultation;
import app.feign.AnimalInterface;
import app.feign.DoctorInterface;
import app.repository.ConsultationRepository;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ConsultationServiceImpl implements ConsultationService {

    private static final String EXCEL_TEMPLATE_PATH = "templates/Raport_Consultari.xlsx";
    private static final String WORD_TEMPLATE_PATH = "templates/Raport_Consultari.docx";

    private final ConsultationRepository consultationRepository;

    DoctorInterface doctorInterface;

    AnimalInterface animalInterface;

    public ConsultationServiceImpl(ConsultationRepository consultationRepository, DoctorInterface doctorInterface, AnimalInterface animalInterface) {
        this.consultationRepository = consultationRepository;
        this.doctorInterface = doctorInterface;
        this.animalInterface = animalInterface;
    }

    @Override
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
                    .price(consultation.getPrice())
                    .build();
            consultations.add(consultationDTO);
        });

        return consultations;
    }
    @Override
    public Consultation getConsultationById(int id) {
        log.info("Fetching consultation by ID: {}", id);
        Optional<Consultation> consultation = consultationRepository.findById(id);
        return consultation.orElse(null);
    }
    @Override
    public Consultation addConsultation(ConsultationDTO consultation) {
        DoctorResponse doctor = doctorInterface.getDoctorByLastName(consultation.getDoctorLastName());

//        TODO: Check if the animal exists if it does use a one to many on the animal side
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

    @Override
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
                .price(updatedConsultation.getPrice())
                .build();

        log.info("Consultation updated successfully");
        return consultationRepository.save(updatedConsultationEntity);
    }

    @Override
    public void deleteConsultation(int id) {
        log.info("Deleting consultation with ID: {}", id);
        consultationRepository.deleteById(id);
    }
    @Override
    public byte[] generateExcelReport() {
        List<Consultation> consultations = consultationRepository.findAll();

        try (InputStream templateStream = new ClassPathResource(EXCEL_TEMPLATE_PATH).getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             Workbook workbook = new XSSFWorkbook(templateStream)) {

            // Assuming there is only one sheet in the workbook
            Sheet sheet = workbook.getSheetAt(0);

            // Header row
            Row headerRow = sheet.createRow(1);
            createStyledExcelCell(headerRow, 0, "Date");
            createStyledExcelCell(headerRow, 1, "Doctor ID");
            createStyledExcelCell(headerRow, 2, "Animal ID");
            createStyledExcelCell(headerRow, 3, "Diagnostic");
            createStyledExcelCell(headerRow, 4, "Treatment");
            createStyledExcelCell(headerRow, 5, "Recommendations");
            createStyledExcelCell(headerRow, 6, "Price");

            // Bold the entire header row
            boldRow(headerRow);

            int rowNum = 2; // Start populating data from the second row

            // Data rows
            for (Consultation consultation : consultations) {
                Row row = sheet.createRow(rowNum++);
                createStyledExcelCell(row, 0, consultation.getDate().toString().substring(0,19));
                createStyledExcelCell(row, 1, String.valueOf(consultation.getDoctorId()));
                createStyledExcelCell(row, 2, String.valueOf(consultation.getAnimalId()));
                createStyledExcelCell(row, 3, consultation.getDiagnostic());
                createStyledExcelCell(row, 4, consultation.getTreatment());
                createStyledExcelCell(row, 5, consultation.getRecommendations());
                createStyledExcelCell(row, 6, String.valueOf(consultation.getPrice()));
            }

            Row totalRow = sheet.createRow(rowNum);
            createStyledExcelCell(totalRow, 5, "Total Price");
            createStyledExcelCell(totalRow, 6, calculateTotalPrice(consultations));

//            TODO: Auto-size columns for dockerfile
// Auto-size columns
//            for (int i = 0; i < sheet.getRow(0).getPhysicalNumberOfCells(); i++) {
//                sheet.autoSizeColumn(i);
//            }

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            log.error("Error occurred while exporting to Excel: {}", e.getMessage());
            return new byte[0];
        }
    }

    @Override
    public byte[] generateWordReport() {
        List<Consultation> consultations = consultationRepository.findAll();

        try (InputStream templateStream = new ClassPathResource(WORD_TEMPLATE_PATH).getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             XWPFDocument document = new XWPFDocument(templateStream)) {

            // Check if there is at least one table in the document
            if (document.getTables().isEmpty())
                throw new NoContentException("No tables found in the Word document template.");

            // Retrieve the first table in the document
            XWPFTable table = document.getTables().getFirst();

            // Header row
            XWPFTableRow headerRow = table.getRow(0);
            createStyledCellWord(headerRow, 0, "Date", true);
            createStyledCellWord(headerRow, 1, "Doctor ID", true);
            createStyledCellWord(headerRow, 2, "Animal ID", true);
            createStyledCellWord(headerRow, 3, "Diagnostic", true);
            createStyledCellWord(headerRow, 4, "Treatment", true);
            createStyledCellWord(headerRow, 5, "Recommendations", true);
            createStyledCellWord(headerRow, 6, "Price", true);

            // Bold the entire header row
            boldRowWord(headerRow);

            // Data rows
            for (Consultation consultation : consultations) {
                XWPFTableRow row = table.createRow();
                // Format the Date column
                createStyledCellWord(row, 0, consultation.getDate(), false);
                createStyledCellWord(row, 1, String.valueOf(consultation.getDoctorId()), false);
                createStyledCellWord(row, 2, String.valueOf(consultation.getAnimalId()), false);
                createStyledCellWord(row, 3, consultation.getDiagnostic(), false);
                createStyledCellWord(row, 4, consultation.getTreatment(), false);
                createStyledCellWord(row, 5, consultation.getRecommendations(), false);
                createStyledCellWord(row, 6, String.valueOf(consultation.getPrice()), false);
            }

            // Add a row for the total price at the bottom
            XWPFTableRow totalRow = table.createRow();
            createStyledCellWord(totalRow, 5, "Total Price", true);
            createStyledCellWord(totalRow, 6, calculateTotalPrice(consultations), true);

            // Auto-adjust column width
            setColumnWidthsWord(table);

            document.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            log.error("Error occurred while exporting to Excel: {}", e.getMessage());
            return new byte[0];
        }
    }
//    UTILS
    private void createStyledExcelCell(Row row, int cellIndex, String text) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(text);

        // Create cell style
        CellStyle style = row.getSheet().getWorkbook().createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Create font
        Font font = row.getSheet().getWorkbook().createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 14);

        style.setFont(font);

        // Apply the style to the cell
        cell.setCellStyle(style);

    }

    private void boldRowWord(XWPFTableRow row) {
        for (XWPFTableCell cell : row.getTableCells()) {
            XWPFParagraph paragraph = cell.getParagraphArray(0);
            XWPFRun run = paragraph.getRuns().getFirst();
            run.setBold(true);
        }
    }

    private void setColumnWidthsWord(XWPFTable table) {
        int numColumns = table.getRow(0).getTableCells().size();

        // Set a default width or adjust based on your estimation
        int defaultColumnWidth = 2000; // Set to a value that suits your needs

        for (int col = 0; col < numColumns; col++) {
            table.getRow(0).getCell(col).getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(defaultColumnWidth));
        }
    }
    private void createStyledCellWord(XWPFTableRow row, int cellIndex, Object value, boolean isHeader) {
        XWPFTableCell cell = row.getCell(cellIndex);

        // Create cell style
        CTTcPr tcPr = cell.getCTTc().isSetTcPr() ? cell.getCTTc().getTcPr() : cell.getCTTc().addNewTcPr();
        CTVerticalJc valign = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr.addNewVAlign();
        valign.setVal(STVerticalJc.CENTER); // Set vertical alignment to CENTER

        // Create paragraph and run for the cell
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFRun run = !paragraph.getRuns().isEmpty() ? paragraph.getRuns().getFirst() : paragraph.createRun();

        // Create font
        if (isHeader) {
            run.setBold(true);
            run.setFontSize(16);
        }

        // Apply the style to the cell
        if (value != null) {
            run.setText(value.toString());
        }
    }
    private void boldRow(Row row) {
        for (Cell cell : row) {
            CellStyle style = cell.getCellStyle();
            Font font = row.getSheet().getWorkbook().createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 16);
            style.setFont(font);
            cell.setCellStyle(style);
        }
    }


    private String calculateTotalPrice(List<Consultation> consultations) {
        int totalPrice = consultations.stream().mapToInt(Consultation::getPrice).sum();
        return String.valueOf(totalPrice);
    }
}
