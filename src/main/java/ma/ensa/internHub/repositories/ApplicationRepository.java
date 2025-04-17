package ma.ensa.internHub.repositories;

import ma.ensa.internHub.domain.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    List<Application> findByInternshipCompanyId(UUID companyId);

}
