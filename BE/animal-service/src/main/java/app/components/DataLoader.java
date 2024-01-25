package app.components;

import app.entity.Animal;
import app.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final AnimalRepository animalRepository;

    @Autowired
    public DataLoader(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void run(String... args) {
        animalRepository.save(new Animal(1, "Bella", "Dog", "Maltese" , 2 ,  100.0,
                "AC0001" , false,  "animal-service/src/main/resources/images/animals/Bella.jpg" ));
        animalRepository.save(new Animal(2, "Luna", "Cat", "Birmanese" , 2 ,  50.0,
                "AC0002" , false,  "animal-service/src/main/resources/images/animals/Luna.jpg" ));
        animalRepository.save(new Animal(3, "Pipu", "Hamster", "Dwarf" , 1 ,  15.0,
                "AC0003" , false,  "animal-service/src/main/resources/images/animals/Pipu.jpg" ));
        animalRepository.save(new Animal(4, "Topolina", "Cat", "Siamese" , 3 ,  120.0,
                "AC0004" , false,  "animal-service/src/main/resources/images/animals/Topolina.jpg" ));
    }
}
