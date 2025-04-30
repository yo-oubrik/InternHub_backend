package ma.ensa.internHub.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.enums.ReportStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFlagResponse {
    private UUID id;
    private String reason;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ReportStatus reportStatus;
    private String studentId;
    private String companyId;
    private String firstName;
    private String lastName;
    private String companyName;
    private String studentEmail;
    private String companyEmail;
    private List<String> screenshots;
}
