package ma.ensa.internHub.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import ma.ensa.internHub.validation.Phone;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("COMPANY")
public class Company extends User {
    @NotBlank(message = "Company name is required")
    private String name;

    @Column(columnDefinition = "TEXT", length = 10000)
    @Size(min = 10, max = 10000, message = "Description must be between 10 and 10000 characters")
    private String description;

    @Pattern(regexp = "^\\d{15}$", message = "ICE must be exactly 15 digits")
    private String ice;

    @Phone
    private String tel;

    @Embedded
    private MapLocation location;

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

    private boolean blocked;

    private LocalDateTime blockedAt;

    @Override
    public Role getRole() {
        return Role.COMPANY;
    }

    @Override
    public String getName() {
        return name;
    }
}
