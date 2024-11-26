package coffee_manager.service.impl;

import coffee_manager.config.jwt.AuthUserDetail;
import coffee_manager.model.User;
import coffee_manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> findEmail = this.userRepository.findByEmail(email);
        Collection<GrantedAuthority> roles;
        if(findEmail.isPresent()) {
            roles = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
            return new AuthUserDetail(findEmail.get(), roles);
        } else {
            throw new UsernameNotFoundException("Employee not found with username: " + email);
        }
    }
}
