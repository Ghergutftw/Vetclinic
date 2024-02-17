package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private List<String> ownedAnimals;
}
