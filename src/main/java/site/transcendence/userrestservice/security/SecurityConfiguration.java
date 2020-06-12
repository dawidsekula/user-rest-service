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

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .antMatchers(HttpMethod.POST, SecurityConstant.SIGN_UP_URL).permitAll() // POST request for signing up URL
//                .antMatchers("/test/admin").hasRole("ADMIN")
//                .antMatchers("/test/user").hasRole("USER")
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
