package africa.semicolon.ppay.infrastructure.config.securityConfig;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {
    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Value("${keycloak.username}")
    private  String username;
    @Value("${keycloak.password}")
    private String password;
    @Autowired
    private JWTAuthConverter jwtAuthConverter;
//

//        @Bean
//        public WebSecurityCustomizer webSecurityCustomizer() {
//            return (web) -> {
//                web.ignoring().requestMatchers(
//                        HttpMethod.POST,
//                        "/public/",
//                        "/api/v1/users/register",
//                        "/api/v1/users/login"
//                );
//                web.ignoring().requestMatchers(
//                        HttpMethod.GET,
//                        "/public/"
//                );
//                web.ignoring().requestMatchers(
//                        HttpMethod.DELETE,
//                        "/public/",
//                        "/users/{id}"
//                );
//                web.ignoring().requestMatchers(
//                        HttpMethod.PUT,
//                        "/public/",
//                        "/users/{id}/send-verification-email",
//                        "/users/forgot-password"
//
//                );
//
//
//            };
//
//
//        }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/v1/users/register","/api/v1/users/login").permitAll()
                    .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                    .jwtAuthenticationConverter(jwtAuthConverter())
                    )
            )
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return httpSecurity.build();
}
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JWTAuthConverter jwtAuthConverter(){
            return new JWTAuthConverter();
    }

}

