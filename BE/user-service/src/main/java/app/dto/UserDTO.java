package app.dto;

import app.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {


    private int id;

    private String username;

    private String email;

    private String password;

    private Roles role;
}
