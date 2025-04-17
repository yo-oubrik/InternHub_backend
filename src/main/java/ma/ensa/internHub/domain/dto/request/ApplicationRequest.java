package ma.ensa.internHub.domain.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.enums.ApplicationStatus;
import ma.ensa.internHub.validation.ValidDate;

import java.time.LocalDateTime;
import java.util.Date;
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

    @NotNull(message = "Application date is required")
    private LocalDateTime applicationDate;

    private String motivationLetter;

    @NotBlank(message = "CV is required")
    private String cv;

}