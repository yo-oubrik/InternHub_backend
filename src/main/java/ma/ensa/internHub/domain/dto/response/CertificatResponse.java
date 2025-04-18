package ma.ensa.internHub.domain.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificatResponse  {

    private UUID id;
    private String title;
    private String thumbnail;
    private String date;
    private StudentResponse studentResponse;
}
