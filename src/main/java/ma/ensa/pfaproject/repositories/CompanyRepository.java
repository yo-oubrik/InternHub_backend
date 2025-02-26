package ma.ensa.pfaproject.repositories;

import ma.ensa.pfaproject.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,String> {
}
