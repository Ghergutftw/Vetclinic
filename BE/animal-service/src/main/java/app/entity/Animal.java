package app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String nickname;

    private String animalType;

    private String specie;

    private int age;

    private double weight;

    @Column(unique = true)
    private String animalCode;

    private Boolean fromAdoption;

    private String pathToImage;

    public Animal() {
        this.pathToImage = "animal-service/src/main/resources/images/animals/default.jpg";
    }

}