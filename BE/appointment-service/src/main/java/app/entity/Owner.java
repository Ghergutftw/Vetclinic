package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "owners")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Email
    private String email;

    private int userId;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<Appointment> appointments = new ArrayList<>();

    @ElementCollection
    @Builder.Default
    private List<Integer> consultations = new ArrayList<>();

    @ElementCollection
    @Builder.Default
    private List<String> ownedAnimals = new ArrayList<>();

}
