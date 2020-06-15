package site.transcendence.userrestservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import site.transcendence.userrestservice.api.users.UserService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public SecurityConfiguration(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // NOTE CSRF is disabled because of using REST interface only (not the web one)
                .csrf().disable().httpBasic()
                .and()

                /*
                Authorization requests
                */
                .authorizeRequests() // Authorize all following requests
                .antMatchers(HttpMethod.POST, SecurityConstant.SIGN_UP_URL).not().authenticated() // User registration
                .antMatchers(HttpMethod.GET, SecurityConstant.EMAIL_VERIFICATION_URL).not().authenticated() // Email confirmation
                .antMatchers(HttpMethod.POST, SecurityConstant.PASSWORD_RESET_REQUEST_URL).permitAll() // Requesting password reset
                .antMatchers(HttpMethod.POST, SecurityConstant.PASSWORD_RESET_URL).permitAll() // Resetting password
                .antMatchers("/swagger-ui.html" ,"/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger UI

                .anyRequest().authenticated() // All requests for authenticated users

                .and()

                /*
                Custom authorization and authentication filters
                 */
                .addFilter(new JWTAuthenticationFilter(authenticationManager())) // JWT authentication (login attempt with token creation)
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userService)) // JWT authorization (authenticating requests with token)

                /*
                Other settings
                */
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Disabling session creation

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }


}
