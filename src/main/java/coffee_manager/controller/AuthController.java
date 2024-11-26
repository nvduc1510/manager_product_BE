package coffee_manager.controller;

import coffee_manager.config.jwt.AuthUserDetail;
import coffee_manager.config.jwt.JWTokenProvider;
import coffee_manager.dto.request.LoginRequest;
import coffee_manager.dto.request.RegisterRequest;
import coffee_manager.dto.response.LoginResponse;
import coffee_manager.service.UserService;
import coffee_manager.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    final JWTokenProvider tokenProvider;
    final AuthenticationManager authenticationManager;
    final UserDetailsServiceImpl userDetailsService;

    AuthController(AuthenticationManager authenticationManager, JWTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = tokenProvider.generateToken((AuthUserDetail) authentication.getPrincipal());
            return ResponseEntity.ok(new LoginResponse(accessToken));
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            log.warn("Authentication failed: ", ex);
            errors.put("code", "100");
            errors.put("message", ex.getMessage());
        } catch (Exception ex) {
            log.warn("Unexpected error: ", ex);
            errors.put("code", "000");
            errors.put("message", "Unexpected error occurred");
        }
        return ResponseEntity.badRequest().body(new LoginResponse(errors));
    }
    @PostMapping("/register")
    private ResponseEntity<?> createUser (@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

}
