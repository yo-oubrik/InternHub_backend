package ma.ensa.internHub.domain.entities;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {
    @NotNull(message = "CreatedAt cannot be null")
    private LocalDateTime createdAt;
    @NotNull(message = "UpdatedAt cannot be null")
    private LocalDateTime updatedAt;
}
