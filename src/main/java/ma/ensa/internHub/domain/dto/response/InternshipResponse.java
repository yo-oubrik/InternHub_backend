package ma.ensa.internHub.domain.dto.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.entities.InternshipType;
import ma.ensa.internHub.domain.entities.SalaryType;
import ma.ensa.internHub.domain.entities.WorkMode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternshipResponse {

    private UUID id;

    private CompanyResponse company;

    private String description;

    private int duration;

    private double salary;

    private SalaryType salaryType;

    private boolean isMotivationLetterRequired;

    private String title;

    private WorkMode workMode;

    private List<InternshipType> tags;

    private List<String> skills;

    private boolean negotiable;

    private boolean paid;
}
