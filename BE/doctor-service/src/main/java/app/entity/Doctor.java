package app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

@Table(name="doctor")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private int userId;

    private String speciality;

    private int age;

    private int yearsOfExperience;

    @Formula("(SELECT COUNT(*) FROM appointments a WHERE a.doctor_id = id)")
    private int numberOfAppointments;
}
