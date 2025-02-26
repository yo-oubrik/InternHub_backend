package ma.ensa.pfaproject.repositories;

import ma.ensa.pfaproject.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

}
