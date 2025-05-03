package ma.ensa.internHub.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.internHub.validation.ValidDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Domain is required")
    private String domain;

    @NotBlank(message = "Diploma is required")
    private String diploma;

    @ValidDate(message = "Start date must be in MMM-yyyy format ")
    private String startDate;

    @ValidDate(message = "End date must be in MMM-yyyy format or 'PRESENT'")
    private String endDate;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Student student;


}
