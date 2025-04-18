package ma.ensa.internHub.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificatRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Thumbnail is required")
    private String thumbnail;

    @NotBlank(message = "Date is required")
    private String date;

    @NotNull(message = "Student ID is required")
    private UUID studentId;

}