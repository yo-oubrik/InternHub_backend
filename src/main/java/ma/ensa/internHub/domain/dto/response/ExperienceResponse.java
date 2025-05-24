package ma.ensa.internHub.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceResponse {

    private UUID id;
    private String poste;
    private String startDate;
    private String endDate;
    private String description;
    private String company;
}
