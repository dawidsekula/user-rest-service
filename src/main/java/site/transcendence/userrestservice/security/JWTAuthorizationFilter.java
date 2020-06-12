package site.transcendence.userrestservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static site.transcendence.userrestservice.security.SecurityConstant.*;

/**
 * Class is responsible for giving authorization to the user after verifying given in header token
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserService userService;

    public JWTAuthorizationFilter(AuthenticationManager authManager, UserService userService) {
        super(authManager);
        this.userService = userService;
    }

    /**
     * Method checks if request contains header with authentication token and adds authentication
     * to the application's security context if existing token is valid
     *
     * Then it passes request and response to next filter in chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // Getting token from header named after constant's SecurityConstant.HEADER_STRING value i.e. Authorization
        String token = request.getHeader(HEADER_STRING);

        // If token is missing or does not start with value set it SecurityConstant.TOKEN_PREFIX
        // JWT authorization cannot be verified and granted so request and response are passed to next filter in chain
        if (token == null || !token.startsWith(TOKEN_PREFIX)){
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
     * @param token value from request's header
     * @return authentication object
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(HEADER_STRING);

        if (token != null){
            String user = JWT.require(Algorithm.HMAC512(TOKEN_SECRET)) // Decrypting token requires the same 'key' it was encrypted by
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, "")) // Verifying token's validation
                    .getSubject(); // Getting subject/user from decrypted token's data

            if (user != null){
                List<GrantedAuthority> authorities = userService.getUserAuthorities(user);
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }

        return null; // If user is null, null is returned giving no authentication
    }


}
