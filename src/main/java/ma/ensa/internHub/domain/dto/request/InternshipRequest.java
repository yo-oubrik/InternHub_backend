package ma.ensa.internHub.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.entities.SalaryType;
import ma.ensa.internHub.domain.entities.WorkMode;
import ma.ensa.internHub.domain.entities.InternshipType;
import ma.ensa.internHub.domain.entities.Location;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternshipRequest {
    @NotBlank(message = "Description is required")
    @Size(min = 120, message = "Description should be at least 120 characters")
    private String description;

    @Positive(message = "Duration must be positive")
    private int duration;

    // @Salary
    private double salary;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "Salary type is required")
    private SalaryType salaryType;

    @NotNull(message = "motivation letter is required")
    private boolean motivationLetterRequired; // replace isMotivationLetterRequired with motivationLetterRequired

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Work mode is required")
    private WorkMode workMode;

    private List<InternshipType> tags;

    @NotNull(message = "At least one skill must be specified")
    @Size(min = 1, message = "You must provide at least one skill")
    private List<@NotBlank(message = "Skill cannot be blank") String> skills;

    private boolean negotiable; // replace Boolean with boolean

    private boolean paid; // replace Boolean with boolean
}
