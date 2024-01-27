package app.entity;

import app.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Table(name = "user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Email
    private String email;

    private String password;

    private Roles role;

}
