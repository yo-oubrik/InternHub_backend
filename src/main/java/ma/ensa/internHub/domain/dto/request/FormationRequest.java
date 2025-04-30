package ma.ensa.internHub.domain.dto.request;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.validation.ValidDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormationRequest {

    @NotBlank(message = "Domain is required")
    private String domain;

    @NotBlank(message = "Diploma is required")
    private String diploma;

    @ValidDate(message = "Start date must be in MMM-yyyy format ")
    private String startDate;

    @ValidDate(message = "End date must be in MMM-yyyy format or 'PRESENT'")
    private String endDate;

    @NotNull(message = "Company ID is required")
    private UUID companyId;

    @NotNull(message = "Student ID is required")
    private UUID studentId;

}
