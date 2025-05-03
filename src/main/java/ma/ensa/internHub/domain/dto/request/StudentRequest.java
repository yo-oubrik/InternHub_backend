package ma.ensa.internHub.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.ensa.internHub.domain.entities.Links;
import ma.ensa.internHub.domain.entities.Location;
import ma.ensa.internHub.validation.Phone;
import ma.ensa.internHub.validation.ValidImageUrl;
import ma.ensa.internHub.validation.ValidName;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StudentRequest extends UserRequest {
    @NotBlank(message = "First name is required")
    @ValidName
    private String firstName;

    @NotBlank(message = "Last name is required")
    @ValidName
    private String lastName;
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

    public String getName() {
        return firstName + " " + lastName;
    }
}