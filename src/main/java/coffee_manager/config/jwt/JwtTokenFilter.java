package coffee_manager.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JWTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtTokenFilter(JWTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = this.getJwtFromRequest(request);
            UsernamePasswordAuthenticationToken authentication = null;
            if(StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {

                if(SecurityContextHolder.getContext().getAuthentication() == null) {
                    String email = this.tokenProvider.getUsernameFromJWT(jwt);
                    UserDetails userDetail = this.userDetailsService.loadUserByUsername(email);

                    if (StringUtils.hasText(userDetail.getUsername())) {
                        authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } else {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("failed on set user authentication", ex);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}