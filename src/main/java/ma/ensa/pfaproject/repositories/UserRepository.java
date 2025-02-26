package ma.ensa.pfaproject.repositories;

import ma.ensa.pfaproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
