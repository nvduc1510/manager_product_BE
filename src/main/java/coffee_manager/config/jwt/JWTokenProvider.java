package coffee_manager.config.jwt;

import coffee_manager.config.Constants;
import coffee_manager.model.UserRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.*;

@Component
public class JWTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JWTokenProvider.class);
    public String generateToken(UserDetails userDetails) {
        Date date_now =new Date();
        Date expirDate =new Date(date_now.getTime() + Constants.JWT_EXPIRATION * 1000);

        return JWT.create()
                .withIssuer("self")
                .withIssuedAt(date_now) //Thời gian phát hành
                .withExpiresAt(expirDate) // Thời gian hết hạn
                .withSubject(userDetails.getUsername()) // Thiết lập đối tượng
                .withClaim("user", toMap(userDetails.getUsername()))
                .sign(Algorithm.HMAC512(Constants.JWT_SECRET));
    }

    private Map<String, Object> toMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            for (Field field : obj.getClass().getDeclaredFields()) {
                MethodHandle getterHandle = lookup.findGetter(obj.getClass(), field.getName(), field.getType());
                Object value = getterHandle.invoke(obj);

                if (field.getType().equals(UserRole.class)) {
                    UserRole userRole = (UserRole) value;
                    if (userRole != null) {
                        Map<String, Object> userRoleMap = new HashMap<>();
                        userRoleMap.put("userRoleId", userRole.getUserRoleId());
                        userRoleMap.put("userRoleName", userRole.getUserRoleName());
                        map.put(field.getName(), userRoleMap);
                    } else {
                        map.put(field.getName(), null);
                    }
                } else if (Arrays.stream(Constants.ATTRIBUTES_TO_TOKEN).anyMatch(field.getName()::equals)) {
                    map.put(field.getName(), value);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return map;
    }

    public String getUsernameFromJWT(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            DecodedJWT token = JWT.require(Algorithm.HMAC512(Constants.JWT_SECRET)).build().verify(authToken);
            Date expireAt = token.getExpiresAt();
            if (expireAt.compareTo(new Date()) > 0) {
                return true;
            }
        } catch (JWTVerificationException ex) {
            log.error("Invalid JWT token");
        }
        return false;
    }

}
