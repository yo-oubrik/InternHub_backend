package ma.ensa.internHub.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.internHub.domain.enums.ReportStatus;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractFlag {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = "Date cannot be null")
    private LocalDateTime date;

    @NotNull(message = "Reason cannot be null")
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @Size(max = 3, message = "Maximum 3 screenshots are allowed")
    private List<String> screenshots;

    @NotNull(message = "Status cannot be null")
    private ReportStatus reportStatus;
}
