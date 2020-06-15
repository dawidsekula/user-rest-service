package site.transcendence.userrestservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;
import site.transcendence.userrestservice.security.SecurityConstant;

import java.time.LocalDateTime;
import java.util.Date;

import static site.transcendence.userrestservice.security.SecurityConstant.*;

@Service
public class JWTUtil {

    public static String generateEmailVerificationToken(String username){
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EMAIL_VERIFICATION_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(TOKEN_SECRET.getBytes()));
    }

    /**
     * NOTE Token is used for authentication only and for security measures authorities SHOULD NOT be included
     */
    public static String generateAuthenticationToken(String username){
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + LOGIN_AUTHENTICATION_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(TOKEN_SECRET.getBytes()));
    }

    public static String generatePasswordResetToken(String username){
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + PASSWORD_RESET_TOKEN_EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(TOKEN_SECRET.getBytes()));
    }

    public static boolean hasTokenExpired(String token){
        //        try {
        //            JWTVerifier verifier = JWT
        //                    .require(Algorithm.HMAC512(TOKEN_SECRET))
        //                    .build();
        //            verifier.verify(token);
        //        } catch (JWTVerificationException exception){
        //            throw new RuntimeException(exception);
        //        }
        //        return false;
        return JWT.decode(token).getExpiresAt().before(new Date());
    }


}
