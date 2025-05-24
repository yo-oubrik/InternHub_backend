package ma.ensa.internHub.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.enums.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @NotNull(message = "Student is required")
    private Student student;

    @ManyToOne
    @NotNull(message = "Internship is required")
    private Internship internship;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    @NotNull(message = "Application date is required")
    private LocalDateTime applicationDate;

    private String motivationLetter;

    @NotBlank(message = "CV is required")
    private String cv;

    // add this function to manually enter the value of applicationDate
    @PrePersist
    private void onCreate() {
        this.applicationDate = LocalDateTime.now();
    }

}