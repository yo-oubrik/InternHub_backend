package ma.ensa.pfaproject.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Student extends User{
    private String firstName;
    private String lastName;
    private String profilePicture;

    public Student(String email, String firstName, String lastName, String profilePicture) {
        super(email, Role.STUDENT); 
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
    }
}
