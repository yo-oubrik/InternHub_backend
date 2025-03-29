package ma.ensa.internHub.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.validation.ValidImageUrl;
import ma.ensa.internHub.validation.ValidName;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    @NotBlank(message = "First name is required")
    @ValidName
    private String firstName;

    @NotBlank(message = "Last name is required")
    @ValidName
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(message = "Password should be at least 8 characters", min = 8)
    private String password;

    @ValidImageUrl
    private String profilePicture;
}