package coffee_manager.service;

import coffee_manager.dto.request.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    Boolean createUser(RegisterRequest request);

}
