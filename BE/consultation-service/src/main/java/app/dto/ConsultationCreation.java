package app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConsultationCreation {
    AnimalDTO animalDTO;
    OwnerDTO ownerDTO;
}
