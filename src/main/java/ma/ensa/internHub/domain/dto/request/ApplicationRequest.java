package ma.ensa.internHub.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.enums.ApplicationStatus;

import java.util.UUID;

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

    // remove applicationDate => persist it manually with @Prepersist annotation in
    // Application entity

    @NotBlank(message = "CV is required")
    private String cv;

}