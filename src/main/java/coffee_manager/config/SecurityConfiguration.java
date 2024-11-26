package coffee_manager.config;

import coffee_manager.config.jwt.AuthEntryPoint;
import coffee_manager.config.jwt.JWTokenProvider;
import coffee_manager.config.jwt.JwtTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private final JWTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(JWTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter(this.tokenProvider, this.userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http.cors().and().csrf().disable();

        // Apply headers and disable frameOptions
        http.headers().frameOptions().disable();

        // Set session management to stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Set permissions on endpoints
        http.authorizeRequests(authz -> authz
                // public endpoints
                .requestMatchers(Constants.ENDPOINTS_PUBLIC).permitAll()
                // private endpoints with roles
                .requestMatchers(Constants.ENDPOINTS_WITH_ROLE).hasRole("USER")
                .requestMatchers(Constants.ENDPOINTS_WITH_ROLE_ADMIN).hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        // Set unauthorized requests exception handler
        http.exceptionHandling().authenticationEntryPoint(new AuthEntryPoint());

        // Token filter
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        if (Constants.IS_CROSS_ALLOW) {
            config.addAllowedOriginPattern("*");
        } else {
            config.addAllowedOrigin("*");
        }
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
