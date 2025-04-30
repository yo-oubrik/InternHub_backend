package ma.ensa.internHub.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Image is required")
    private String image;

    @NotBlank(message = "Link is required")
    private String link;

    @NotNull(message = "Student Id is required" )
    private UUID studentId;

}