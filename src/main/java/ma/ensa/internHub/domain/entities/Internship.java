package ma.ensa.internHub.domain.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Company is required")
    @ManyToOne
    private Company company;

    @NotBlank(message = "Description is required")
    @Size(min = 120, message = "Description should be at least 120 characters")
    @Column(columnDefinition = "TEXT")
    private String description;

    @Positive(message = "Duration must be positive")
    private int duration;

    private double salary;

    @NotNull(message = "Salary type is required")
    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Work mode is required")
    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<InternshipType> tags;

    @NotNull(message = "At least one skill must be specified")
    @Size(min = 1, message = "You must provide at least one skill")
    @ElementCollection
    private List<@NotBlank(message = "Skill cannot be blank") String> skills;

    @NotNull(message = "negotiable status is required")
    private boolean negotiable;

    @NotNull(message = "motivation letter is required")
    private boolean isMotivationLetterRequired;

    private boolean paid;

}
