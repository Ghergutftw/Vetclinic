package app.repository;


import app.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Integer> {
    Animal findOneById(int id);

    Animal findOneByAnimalCode(String animalCode);

    Animal findAnimalByNicknameAndOwnerIdAndAnimalTypeAndSpecie(String nickname, int ownerId,String animalType,String specie);
}
