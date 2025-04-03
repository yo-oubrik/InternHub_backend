package ma.ensa.internHub.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ma.ensa.internHub.domain.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT MONTH(c.createdAt), COUNT(c) FROM Company c GROUP BY MONTH(c.createdAt)")
    List<Object[]> countCompaniesByMonth();
}
