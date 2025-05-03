package ma.ensa.internHub.domain.entities;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ma.ensa.internHub.validation.ValidName;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class PendingStudent extends PendingUser {
    @NotBlank(message = "First name is required")
    @ValidName
    private String firstName;
    @NotBlank(message = "First name is required")
    @ValidName
    private String lastName;
}
