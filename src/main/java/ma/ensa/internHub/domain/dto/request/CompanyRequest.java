package ma.ensa.internHub.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequest {
    @NotBlank(message = "Company name is required")
    private String name;

    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "ICE number is required")
    @Pattern(regexp = "^\\d{15}$", message = "ICE must be exactly 15 digits")
    private String ice;

    @NotBlank(message = "Password is required")
    @Size(message = "Password should be at least 8 characters", min = 8)
    private String password;
}
