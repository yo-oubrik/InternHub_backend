package ma.ensa.internHub.domain.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.internHub.validation.ValidImageUrl;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("COMPANY")
public class Company extends User {
    @NotBlank(message = "Company name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "ICE number is required")
    private String ice;

    @NotBlank(message = "RC number is required")
    private String rc;

    @NotBlank(message = "Domain is required")
    private String domain;

    @ValidImageUrl
    private String logo;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private String size;
    private String socialLinks;
    private String website;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Internship> internships;

}
