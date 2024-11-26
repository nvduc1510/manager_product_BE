package coffee_manager.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Integer userId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "user_sex")
    private String userSex;

    @Column(name = "user_birthdate")
    private Date userBirthdate;

    @Column(name = "user_address", length = 125)
    private String userAddress;

    @Column(name = "user_image")
    private String userImage;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}