package app.controller;

import app.dto.AppointmentDTO;
import app.dto.Response;
import app.entity.Appointment;
import app.enums.Status;
import app.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/appointment-service")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.findAll();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Response addAppointment(@RequestBody AppointmentDTO appointment) {
        return appointmentService.save(appointment);
    }

    @PostMapping("/{appointmentId}/update-status")
    public Appointment updateStatus(@PathVariable int appointmentId, @RequestParam String status) {
        Status newStatus = Status.valueOf(status);
        return appointmentService.updateStatus(appointmentId, newStatus);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteAppointment(@PathVariable int id) {
        return appointmentService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Appointment updateAppointment(@PathVariable int id, @RequestBody Appointment appointment) {
        return appointmentService.update(id, appointment);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Appointment getAppointmentById(@PathVariable int id) {
        return appointmentService.findById(id);
    }
}
