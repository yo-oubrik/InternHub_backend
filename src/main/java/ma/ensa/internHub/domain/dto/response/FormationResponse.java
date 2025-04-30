package ma.ensa.internHub.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormationResponse {

    private UUID id;
    private String domain;
    private String diploma;
    private String startDate;
    private String endDate;
    private CompanyResponse companyResponse;
    private StudentResponse studentResponse;
}
