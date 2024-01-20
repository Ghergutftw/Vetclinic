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
@RequestMapping("/${services.appointment.name}")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentService.findAll();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment addAppointment(@RequestBody AppointmentDTO appointment) {
        return appointmentService.save(appointment);
    }

    @PostMapping("/{appointmentId}/update-status/{status}")
    public Appointment updateStatus(@PathVariable int appointmentId, @PathVariable String status) {
        Status newStatus = Status.valueOf(status);
        return appointmentService.updateStatus(appointmentId, newStatus);
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteAppointment(@PathVariable int id) {
        return appointmentService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Appointment updateAppointment(@PathVariable int id, @RequestBody Appointment appointment) {
        return appointmentService.update(id, appointment);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Appointment getAppointmentById(@PathVariable int id) {
        return appointmentService.findById(id);
    }
}
