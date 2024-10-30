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

import java.util.List;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfig {
    private List<String> endPoints = List.of(
            "http://localhost:8085/api/v1/users/reset_password",
            "http://localhost:8085/api/v1/users/change_pin",
            "http://localhost:8085/api/v1/transaction/getALlTransactions/**",
            "http://localhost:8085/api/v1/transaction/getTransaction/**",
            "http://localhost:8085/api/v1/wallet/deposit",
            "http://localhost:8085/api/v1/prembly/verify",
            "http://localhost:8085/api/v1/users/delete/**",
            "http://localhost:8085/api/v1/users/find_by_id/**"

    );
    @Autowired
    private JWTAuthConverter jwtAuthConverter;

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/v1/users/register","/api/v1/users/login","/api/v1/wallet/pay_completed").permitAll()
                    .requestMatchers("/api/v1/monify/authorize").hasRole("ADMIN")
                    .requestMatchers(endPoints.toArray(new String[0])).hasRole("USERS")
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

