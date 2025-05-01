package ma.ensa.internHub.domain.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationResponse {

    private UUID id;
    private StudentResponse studentResponse;
    private InternshipResponse internshipResponse;
    private String status;
    private LocalDateTime applicationDate;
    private String motivationLetter;
    private String cv;

}