package app.dto;

import app.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private int id;

    private String username;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String password;

    private Roles role;

}
