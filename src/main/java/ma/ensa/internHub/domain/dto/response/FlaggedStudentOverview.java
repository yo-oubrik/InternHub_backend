package ma.ensa.internHub.domain.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlaggedStudentOverview {
    private UUID studentId;
    private String firstName;
    private String lastName;
    private String email;
    private long unresolvedFlagsCount;
    private LocalDateTime lastFlagDate;
}
