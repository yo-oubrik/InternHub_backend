package ma.ensa.internHub.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Certificat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Thumbnail is required")
    private String thumbnail;

    @NotBlank(message = "Date is required")
    private String date;

    @ManyToOne
    private Student student;


}

