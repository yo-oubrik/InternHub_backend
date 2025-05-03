package ma.ensa.internHub.domain.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.internHub.validation.ValidDate;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Poste is required")
    private String poste;

    @ValidDate(message = "Start date must be in MMM-yyyy format ")
    private String startDate;

    @ValidDate(message = "End date must be in MMM-yyyy format or 'PRESENT'")
    private String endDate;

    @NotBlank(message = "Description is required")
    private String description;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Student student;

}
