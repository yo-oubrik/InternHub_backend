package ma.ensa.internHub.domain.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("COMPANY")
public class Company extends User {
    @NotBlank(message = "Company name is required")
    private String name;

    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "ICE number is required")
    @Pattern(regexp = "^\\d{15}$", message = "ICE must be exactly 15 digits")
    private String ice; // Changed from Long to String to preserve leading zeros

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Internship> internships;
    // @ValidImageUrl
    // private String logo;

    // size
    // private String size;

}
