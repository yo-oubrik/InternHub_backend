package ma.ensa.pfaproject.repositories;

import ma.ensa.pfaproject.entities.Internship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InternshipRepository extends JpaRepository<Internship,Long> {
}
