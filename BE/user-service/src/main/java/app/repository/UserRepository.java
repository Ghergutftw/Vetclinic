package app.repository;

import app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findOneById(int id);

    Optional<User> findByUsernameOrEmail(String email, String email1);
    User findOneByUsername(String username);
}
