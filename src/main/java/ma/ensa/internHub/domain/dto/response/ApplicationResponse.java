package ma.ensa.internHub.domain.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationResponse {

    private String id;
    private StudentResponse studentResponse;
    private InternshipResponse internshipResponse;
    private String status;
    private LocalDateTime applicationDate;
    private String motivationLetter;
    private String cv;

}