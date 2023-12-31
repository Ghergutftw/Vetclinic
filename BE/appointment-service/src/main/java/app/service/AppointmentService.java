package app.service;

import app.dto.AppointmentDTO;
import app.dto.Response;
import app.entity.Appointment;
import app.enums.Status;

import java.util.List;

public interface AppointmentService {

    List<AppointmentDTO> findAll();

    Appointment updateStatus(int appointmentId, Status newStatus);

    Appointment findById(int id);

    Appointment save(AppointmentDTO appointment);

    Response deleteById(int id);

    Appointment update(int id, Appointment appointment);


}
