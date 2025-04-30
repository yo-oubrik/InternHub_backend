package ma.ensa.internHub.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ma.ensa.internHub.utils.VerificationCodeGenerator;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class PendingUser {
    private static final long EXPIRY_DURATION_SECONDS = 180;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(message = "Password should be at least 8 characters", min = 8)
    private String password;

    private String confirmationCode;

    private LocalDateTime expiryDate;

    @PrePersist
    private void generateConfirmationCode() {
        this.confirmationCode = VerificationCodeGenerator.generateVerificationCode();
        this.expiryDate = LocalDateTime.now().plusSeconds(EXPIRY_DURATION_SECONDS);
    }
}
