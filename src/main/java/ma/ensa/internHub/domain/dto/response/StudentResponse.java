package ma.ensa.internHub.domain.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.ensa.internHub.domain.entities.Links;
import ma.ensa.internHub.domain.entities.Location;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createdAt;
    private String tel;
    private String school;
    private Location location;
    private String profileTitle;
    private String profilePicture;
    private String profileDescription;
    private Links links;
    private boolean blocked;
    private LocalDateTime blockedAt;
}