package ma.ensa.internHub.domain.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import ma.ensa.internHub.validation.ValidGithubUrl;
import ma.ensa.internHub.validation.ValidLinkedInUrl;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Links {
    @ValidGithubUrl
    private String github;
    
    @ValidLinkedInUrl
    private String linkedin;
    
    private String cv;
    private String website;
}
