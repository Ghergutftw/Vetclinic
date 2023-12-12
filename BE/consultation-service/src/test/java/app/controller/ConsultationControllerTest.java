package app.controller;

import app.entity.Consultation;
import app.service.ConsultationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsultationControllerTest {

    @Mock
    private ConsultationService consultationService;

    @InjectMocks
    private ConsultationController consultationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllConsultations() {
        // Arrange
        List<Consultation> consultations = Arrays.asList(new Consultation(), new Consultation());
        when(consultationService.getAllConsultations()).thenReturn(consultations);

        // Act
        ResponseEntity<List<Consultation>> responseEntity = consultationController.getAllConsultations();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(consultations, responseEntity.getBody());
    }

    @Test
    void testGetConsultationById() {
        // Arrange
        int consultationId = 1;
        Consultation consultation = new Consultation();
        when(consultationService.getConsultationById(consultationId)).thenReturn(consultation);

        // Act
        ResponseEntity<Consultation> responseEntity = consultationController.getConsultationById(consultationId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(consultation, responseEntity.getBody());
    }

    @Test
    void testGetConsultationByIdNotFound() {
        // Arrange
        int consultationId = 1;
        when(consultationService.getConsultationById(consultationId)).thenReturn(null);

        // Act
        ResponseEntity<Consultation> responseEntity = consultationController.getConsultationById(consultationId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    // Similar tests for other controller methods (addConsultation, updateConsultation, deleteConsultation) can be written.
}
