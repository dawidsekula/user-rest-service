package site.transcendence.userrestservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import site.transcendence.userrestservice.api.users.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static site.transcendence.userrestservice.security.SecurityConstant.*;

/**
 * Class is responsible for giving authentication to the user after successful verification of given
 * JSON Web Token in the request's header
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserService userService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserService userService) {
        super(authManager);
        this.userService = userService;
    }

    /**
     * Method checks if request contains header with JSON Web Token and adds positive authentication
     * to the application's security context if passed token is valid
     *
     * Then it passes request and response to next filter in chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // Getting JSON Web Token from header
        String token = request.getHeader(HEADER_STRING);

        // If token is missing or does not start with constant's SecurityConstant.TOKEN_PREFIX value then
        // authorization cannot be verified and authentication granted so request and response are passed
        // to next filter in chain
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // Token is passed to getAuthentication() method which returns authentication based on valid token
        UsernamePasswordAuthenticationToken authentication = getAuthentication(token);

        // Passing authentication to application's security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Passing request and response to the next filter in chain
        chain.doFilter(request, response);
    }

    /**
     * Method gets token for validation and returns authentication if token is valid
     *
     * @param token JSON Web Token from request's header
     * @return authentication for token's subject along with authorities of this subject
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String user = JWT.require(Algorithm.HMAC512(TOKEN_SECRET)) // Decrypting token requires the same 'key' it was signed with
                .build()
                .verify(token.replace(TOKEN_PREFIX, "")) // Verifying token's validation
                .getSubject(); // Getting subject/user from token's data

        if (user != null) { // If user is not null, it's authorities are checked and passed to authentication
            List<GrantedAuthority> authorities = userService.getUserAuthorities(user);
            return new UsernamePasswordAuthenticationToken(user, null, authorities);
        }

        return null; // If user is null, null is returned giving no authentication
    }


}
