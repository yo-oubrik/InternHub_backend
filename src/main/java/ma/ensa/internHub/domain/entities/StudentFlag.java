package ma.ensa.internHub.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudentFlag extends AbstractFlag {

    @ManyToOne
    @JoinColumn(name = "flagged_student_id", nullable = false)
    private Student flaggedStudent;

    @ManyToOne
    @JoinColumn(name = "flagged_by_company_id", nullable = false)
    private Company flaggedByCompany;
}
