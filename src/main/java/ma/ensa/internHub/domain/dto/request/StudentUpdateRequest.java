package ma.ensa.internHub.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.entities.Links;
import ma.ensa.internHub.domain.entities.Location;
import ma.ensa.internHub.validation.Phone;
import ma.ensa.internHub.validation.ValidImageUrl;
import ma.ensa.internHub.validation.ValidName;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {
    @NotBlank(message = "First name is required")
    @ValidName
    private String firstName;

    @NotBlank(message = "Last name is required")
    @ValidName
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    private String school;

    private Location location;

    @Phone
    private String tel;

    @ValidImageUrl
    private String profilePicture;

    private String profileDescription;

    private Links links;

    @Size(message = "Title should be at most 30 characters", max = 30)
    private String profileTitle;
}
