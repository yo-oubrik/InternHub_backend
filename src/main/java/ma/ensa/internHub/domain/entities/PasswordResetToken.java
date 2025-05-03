package ma.ensa.internHub.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PasswordResetToken {
    public static final int PASSWORD_RESET_TOKEN_VALIDITY = 180;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Email(message = "Email should be valid")
    @NotNull(message = "Email should not be null")
    private String email;

    @PrePersist
    private void prePersist() {
        this.expiryDate = LocalDateTime.now().plusSeconds(PASSWORD_RESET_TOKEN_VALIDITY);
        this.token = UUID.randomUUID().toString();
    }
}