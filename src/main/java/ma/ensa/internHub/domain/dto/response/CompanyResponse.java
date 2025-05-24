package ma.ensa.internHub.domain.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.entities.Links;
import ma.ensa.internHub.domain.entities.MapLocation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyResponse {
    private UUID id;
    private String name;
    private String description;
    private String email;
    private String ice;
    private String tel;
    private MapLocation location;
    private LocalDateTime createdAt;
    private String profilePicture;
    private Links links;
    private boolean blocked;
    private LocalDateTime blockedAt;
}
