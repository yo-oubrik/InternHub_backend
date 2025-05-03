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
public class FlaggedCompanyOverview {
    private UUID companyId;
    private String name;
    private String email;
    private long unresolvedFlagsCount;
    private LocalDateTime lastFlagDate;
}