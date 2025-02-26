package ma.ensa.pfaproject.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Company extends User{
    private String name;
    private String address;
    private Date createdAt;
    private String description;
    private String ice;
    private String rc;
    private String domain;
    private String logo;
    private String phone;
    private String size;

//    private boolean isFlagged;

    private String socialLinks;

    private Date updatedAt;
    private String website;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Internship> internships;

}
