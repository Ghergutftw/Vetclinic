package app.dto;

import app.enums.Roles;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
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
