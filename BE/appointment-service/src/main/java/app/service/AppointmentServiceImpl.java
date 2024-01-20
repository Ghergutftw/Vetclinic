package app.service;

import app.dto.AppointmentDTO;
import app.dto.DoctorResponse;
import app.dto.Response;
import app.entity.Appointment;
import app.entity.Owner;
import app.enums.Status;
import app.feign.DoctorInterface;
import app.repository.AppointmentRepository;
import app.repository.OwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final OwnerRepository ownerRepository;

    DoctorInterface doctorInterface;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, OwnerRepository ownerRepository, DoctorInterface doctorInterface) {
        this.appointmentRepository = appointmentRepository;
        this.ownerRepository = ownerRepository;
        this.doctorInterface = doctorInterface;
    }


    @Override
    public List<AppointmentDTO> findAll() {
        log.info("Fetching all appointments from the database.");
        List<Appointment> appointments = appointmentRepository.findAll();
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
        appointments.forEach(appointment -> {
            AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                    .id(appointment.getId())
                    .appointmentDate(appointment.getAppointmentDate())
                    .finishedDate(appointment.getFinishedDate())
                    .owner(appointment.getOwner())
                    .status(appointment.getStatus())
                    .reason(appointment.getReason())
                    .doctorLastName(doctorInterface.getDoctor(appointment.getDoctorId()).getLastName())
                    .build();

            appointmentDTOs.add(appointmentDTO);
        });

        return appointmentDTOs;
    }

    @Override
    public Appointment updateStatus(int appointmentId, Status newStatus) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));

        if (newStatus == Status.FINISHED) {
            appointment.setFinishedDate(Timestamp.from(Instant.now()));
            log.warn("Appointment with id {} finished", appointmentId);
        } else if (newStatus == Status.IN_PROGRESS) {
//          todo:  Leaga de consultatie cumva

        }

        appointment.setStatus(newStatus);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment findById(int id) {
        log.info("Fetching appointment with ID: {}", id);
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        return optionalAppointment.orElse(null);
    }

    @Override
    public Appointment save(AppointmentDTO appointmentDTO) {
        log.info("Saving appointment to the database.");
        DoctorResponse doctorResponse = doctorInterface.getDoctorByLastName(appointmentDTO.getDoctorLastName());
        log.info("Adding appointment to the doctor with ID: {}", doctorResponse.getId());
        Owner existingOwner = ownerRepository.findByEmail(appointmentDTO.getOwner().getEmail());


        if (existingOwner == null) {
            Owner newOwner = Owner.builder()
                    .email(appointmentDTO.getOwner().getEmail())
                    .firstName(appointmentDTO.getOwner().getFirstName())
                    .phoneNumber(appointmentDTO.getOwner().getPhoneNumber())
                    .lastName(appointmentDTO.getOwner().getLastName())
                    .build();
            ownerRepository.save(newOwner);
            log.info("Owner with email {} not found. Creating new owner.", appointmentDTO.getOwner().getEmail());
            appointmentDTO.setOwner(newOwner);
        } else {
            log.info("Owner with email {} found.", appointmentDTO.getOwner().getEmail());
            appointmentDTO.setOwner(existingOwner);
        }

        Appointment appointment = Appointment.builder()
                .appointmentDate(appointmentDTO.getAppointmentDate())
                .doctorId(doctorResponse.getId())
                .owner(appointmentDTO.getOwner())
                .reason(appointmentDTO.getReason())
                .build();

        log.info("Saving appointment to the database {}", appointment.getId());
        appointmentRepository.save(appointment);
        log.info("Appointment saved successfully.");
        return appointment;
    }

    @Override
    public Response deleteById(int id) {
        log.info("Deleting appointment with ID: {}", id);
        appointmentRepository.deleteById(id);
        log.info("Appointment deleted successfully.");
        return new Response("success", "Appointment deleted successfully");
    }

    @Override
    public Appointment update(int id, Appointment appointment) {
        log.info("Updating appointment with ID: {}", id);
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isPresent()) {
            appointment.setId(id);
            return appointmentRepository.save(appointment);
        } else {
            log.warn("Appointment with ID {} not found. Update failed.", id);
            return null;
        }
    }


}
