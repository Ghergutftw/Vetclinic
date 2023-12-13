package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDTO {
    private int id;

    private Date date;

    private String doctorLastName;

    private String diagnostic;

    private String treatment;

    private String recommendations;

    private AnimalDTO animal;
}
