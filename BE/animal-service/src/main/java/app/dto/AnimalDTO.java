package app.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {

    private int id;

    private String nickname;

    private String animalType;

    private String specie;

    private int age;

    private double weight;

    @Column(unique = true)
    private String animalCode;

    private Boolean forAdoption;

    private int ownerId;

}