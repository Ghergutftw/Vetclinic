package app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {


    private String firstName;

    private String lastName;

    private UserDTO user;

    private String speciality;

    private int age;

    private int yearsOfExperience;
}