package tech.getarrays.associationsministry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.getarrays.associationsministry.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}
