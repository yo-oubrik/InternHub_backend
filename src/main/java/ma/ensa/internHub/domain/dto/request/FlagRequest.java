package ma.ensa.internHub.domain.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlagRequest {
    @NotNull(message = "Target ID cannot be null")
    private UUID targetId;

    @NotNull(message = "Reason cannot be null")
    private String reason;

    private String description;

    @Size(max = 3, message = "Maximum 3 screenshots are allowed")
    private List<String> screenshots;
}
