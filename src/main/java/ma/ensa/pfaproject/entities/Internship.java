package ma.ensa.pfaproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Internship {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String createdBy;

    @ManyToOne
    private Company company;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private String description;

    private int duration;
    private double salary;

    @Enumerated(EnumType.STRING)
    private SalaryType salaryType;

    private String domain;
    private String location;
    private String title;

    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    private WorkMode workMode;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<InternshipType> tags;

    @ElementCollection
    private List<String> skills;

    private boolean negotiable;
    private boolean remunerated;

}
