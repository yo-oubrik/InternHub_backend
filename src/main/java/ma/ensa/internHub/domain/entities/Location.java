package ma.ensa.internHub.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class Location {
    @NotNull(message = "Country is required")
    private String country;
    @NotNull(message = "City is required")
    private String city;
    @NotNull(message = "Address is required")
    private String address;
    
}

