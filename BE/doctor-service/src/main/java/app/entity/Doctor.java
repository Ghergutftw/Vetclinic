package app.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name="doctor")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String speciality;

    private int age;

    private int yearsOfExperience;
}
