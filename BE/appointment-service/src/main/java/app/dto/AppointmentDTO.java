package app.dto;

import app.entity.Owner;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;


@Data
@Builder
public class AppointmentDTO {
    private Owner owner;

    private Timestamp appointmentDate;

    private String reason;

    private String doctorLastName;
}
