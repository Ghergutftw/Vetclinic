package app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    private Boolean forAdoption;
    @JoinColumn(nullable = true)
    private int ownerId;

    @Lob
    @Column(name = "image_data", length = 1048576)
    private byte[] imageData;

    @Formula("(SELECT COUNT(*) FROM appointments a WHERE a.doctor_id = id)")
    private int numberOfVisits;
}