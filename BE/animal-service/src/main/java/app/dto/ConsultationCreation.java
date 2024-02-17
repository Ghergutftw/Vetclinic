package app.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class ConsultationCreation {
    AnimalDTO animalDTO;
    OwnerDTO ownerDTO;
}
