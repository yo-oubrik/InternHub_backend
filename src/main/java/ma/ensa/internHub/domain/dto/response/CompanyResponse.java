package ma.ensa.internHub.domain.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.entities.Links;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
    private UUID id;
    private String name;
    private String description;
    private String address;
    private String email;
    private String ice;
    private LocalDateTime createdAt;
    private String profilePicture;
    private Links links ;
}
