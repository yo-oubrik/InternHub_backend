package ma.ensa.internHub.domain.entities;

import java.util.List;
import java.util.UUID;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Creator is required")
    private String createdBy;

    @NotNull(message = "Company is required")
    @ManyToOne
    private Company company;

    @NotBlank(message = "Description is required")
    private String description;

    @Positive(message = "Duration must be positive")
    private int duration;

    @Positive(message = "Salary must be positive")
    private double salary;

    @NotNull(message = "Salary type is required")
    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    @NotBlank(message = "Domain is required")
    private String domain;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Work mode is required")
    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<InternshipType> tags;

    @ElementCollection
    private List<String> skills;

    private boolean negotiable;
    private boolean remunerated;

}
