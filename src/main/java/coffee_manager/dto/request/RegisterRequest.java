package coffee_manager.dto.request;

import coffee_manager.model.UserRole;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterRequest {
    private Integer userId;
    private String password;
    private String email;
    private String userFullName;
    private Date userBirthdate;
}
