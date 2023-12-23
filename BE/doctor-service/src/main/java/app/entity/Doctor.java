package app.entity;

import jakarta.persistence.*;
import lombok.*;

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
}
