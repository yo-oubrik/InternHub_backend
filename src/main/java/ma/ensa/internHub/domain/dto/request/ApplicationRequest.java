package ma.ensa.internHub.domain.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.enums.ApplicationStatus;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationRequest {
    @NotNull(message = "Internship Id is required")
    private UUID internshipId;

    @NotNull(message = "Status is required")
    private ApplicationStatus status;

    private String motivationLetter;

    @NotBlank(message = "CV is required")
    private String cv;

}