package ma.ensa.internHub.domain.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.internHub.domain.enums.Role;

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
    private String description;

    @Pattern(regexp = "^\\d{15}$", message = "ICE must be exactly 15 digits")
    private String ice;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Internship> internships;

    @OneToMany(mappedBy = "flaggedByCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<StudentFlag> studentFlagsMade = new ArrayList<>();

    @OneToMany(mappedBy = "flaggedCompany", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CompanyFlag> companyFlagsReceived = new ArrayList<>();

    @Embedded
    private Links links;

    @Override
    public Role getRole() {
        return Role.COMPANY;
    }
}
