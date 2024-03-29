package app.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ConsultationDTO {
    private int id;

    private Date date;

    private String doctorLastName;

    private String animalCode;

    private String diagnostic;

    private String treatment;

    private String recommendations;

    private AnimalDTO consultatedAnimal;

    private int price;

    private OwnerDTO owner;

}
