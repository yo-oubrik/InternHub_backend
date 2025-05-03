package ma.ensa.internHub.domain.entities;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.internHub.domain.enums.Role;
import ma.ensa.internHub.validation.Phone;
import ma.ensa.internHub.validation.ValidName;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("STUDENT")
public class Student extends User {
    @NotBlank(message = "First name is required")
    @ValidName
    private String firstName;

    @NotBlank(message = "Last name is required")
    @ValidName
    private String lastName;

    private String school;

    private String profileTitle;

    @Phone
    private String tel;

    @Embedded
    private Location location;

    @Embedded
    private Links links;

    private String profileDescription;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Formation> formations;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Certificat> certificates;
    private boolean blocked;

    private LocalDateTime blockedAt;

    @OneToMany(mappedBy = "flaggedByStudent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CompanyFlag> companyFlagsMade = new ArrayList<>();

    @OneToMany(mappedBy = "flaggedStudent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<StudentFlag> studentFlagsReceived = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Application> applications = new ArrayList<>();

    @Override
    public Role getRole() {
        return Role.STUDENT;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }
}