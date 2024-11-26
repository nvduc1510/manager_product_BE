package coffee_manager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Integer userRoleId;
    @Column(name = "user_role_name")
    private String userRoleName;

    public Integer getUserRoleId() {
        return userRoleId;
    }
    public String getUserRoleName() {
        return userRoleName;
    }
}
