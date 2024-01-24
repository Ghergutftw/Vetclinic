package app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adoption")
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "animal_id")
    private Integer animalId;

    @Column(name = "adopter_id")
    private Integer adopterId;

    @Column(name = "status")
//    Sa poti vedea daca a fost adoptat sau nu
    private String status;

    @Column(name = "animal_name")
    private String animalName;

    @Column(name = "image_path")
    private String imagePath;



}
