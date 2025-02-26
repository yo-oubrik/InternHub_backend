package ma.ensa.pfaproject.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Inheritance(strategy= InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String email, Role role) {
        this.email = email;
        this.role = role;
    }
}
