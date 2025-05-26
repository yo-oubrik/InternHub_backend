package ma.ensa.internHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ma.ensa.internHub.domain.entities.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT MONTH(s.createdAt), COUNT(s) FROM Student s WHERE YEAR(s.createdAt) = YEAR(CURRENT_DATE) GROUP BY MONTH(s.createdAt)")
    List<Object[]> countStudentsByMonth();

    long countByBlockedTrue();
}