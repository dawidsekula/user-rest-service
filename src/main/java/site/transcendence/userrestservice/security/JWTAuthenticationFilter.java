package site.transcendence.userrestservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import site.transcendence.userrestservice.api.requests.UserLogInRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static site.transcendence.userrestservice.security.SecurityConstant.*;

/**
 * Class is responsible for authenticating user while logging into application and generating personalized token after successful attempt
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * User sends login request with credentials like i.e. username and password
     * Credentials are passed to AuthenticationManager
     *
     * @param request contains object with login credentials needed for acquiring access
     * @return Authentication object with given credentials
     * @throws AuthenticationException when given credentials are not valid and authentication cannot be granted
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Mapping data from request to custom log-in request UserLogInRequest
            UserLogInRequest credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLogInRequest.class);

            // Passing given data to AuthenticationManager
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>())
            );
        }catch (IOException exception){
            throw new RuntimeException(exception);
        }
    }

    /**
     * Successful authentication creates personalized token for the user which is set to the header
     * named in constant SecurityConstant.HEADER_STRING together with SecurityConstant.TOKEN_PREFIX
     * i.e. "Authentication | Bearer <token>"
     *
     * Token valid time is set in constant SecurityConstant.LOGIN_AUTHENTICATION_EXPIRATION_TIME
     * and is encrypted using String value set in constant SecurityConstant.TOKEN_SECRET
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // Creating personalized token
        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername()) // Getting user's username
                .withExpiresAt(new Date(System.currentTimeMillis() + LOGIN_AUTHENTICATION_EXPIRATION_TIME)) // Setting expiration time of token
                .sign(Algorithm.HMAC512(TOKEN_SECRET.getBytes())); // Encrypting token

        // Creating header with created token
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
