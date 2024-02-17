package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionDTO {

    private OwnerDTO owner;
    private String email;
    private String animalCode;
    private Date adoptionDate;
}
