package ma.ensa.internHub.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.entities.InternshipType;
import ma.ensa.internHub.domain.entities.SalaryType;
import ma.ensa.internHub.domain.entities.WorkMode;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternshipRequest {

    @NotNull(message = "Company is required")
    private UUID companyId;

    @NotBlank(message = "Description is required")
    @Size(min = 120, message = "Description should be at least 120 characters")
    private String description;

    @Positive(message = "Duration must be positive")
    private int duration;

    @Positive(message = "Salary must be positive")
    private double salary;

    @NotNull(message = "Salary type is required")
    private SalaryType salaryType;

    @NotBlank(message = "Domain is required")
    private String domain;

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Work mode is required")
    private WorkMode workMode;

    private List<InternshipType> tags;

    @NotNull(message = "At least one skill must be specified")
    @Size(min = 1, message = "You must provide at least one skill")
    private List<@NotBlank(message = "Skill cannot be blank") String> skills;

    @NotNull(message = "Negotiable status is required")
    private Boolean negotiable;

    private Boolean paid;
}
