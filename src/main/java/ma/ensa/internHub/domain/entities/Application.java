package ma.ensa.internHub.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.enums.ApplicationStatus;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    @NotNull(message = "Application date is required")
    private LocalDateTime applicationDate;

    private String motivationLetter;

    @NotBlank(message = "CV is required")
    private String cv;

    @ManyToOne
    @NotNull(message = "Internship is required")
    private Internship internship;
    @ManyToOne
    @NotNull(message = "Student is required")
    private Student student;
}