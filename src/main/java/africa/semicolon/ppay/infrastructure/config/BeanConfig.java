package africa.semicolon.ppay.infrastructure.config;

import africa.semicolon.ppay.application.ports.output.PayStackPaymentOutputPort;
import africa.semicolon.ppay.application.ports.output.TransactionOutputPort;
import africa.semicolon.ppay.application.ports.output.UserOutputPort;
import africa.semicolon.ppay.application.ports.output.WalletOutputPort;
import africa.semicolon.ppay.application.service.*;
import africa.semicolon.ppay.domain.service.*;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.PayStackPaymentEntityRepo;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.TransactionEntityRepo;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.UserEntityRepo;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.WalletEntityRepo;
import africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter.PayStackPaymentPersistenceAdapter;
import africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter.TransactionPersistenceAdapter;
import africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter.UserPersistenceAdapter;
import africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter.WalletPersistenceAdapter;
import com.cloudinary.Cloudinary;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BeanConfig {
    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Bean
    public WalletService walletService(WalletOutputPort walletOutputPort, TransactionService transactionService, PayStackPaymentService payStackPaymentService){
        return new WalletService(walletOutputPort,transactionService, payStackPaymentService);
    };
    @Bean
    public TransactionService transactionService(TransactionOutputPort transactionOutputPort){
        return new TransactionService(transactionOutputPort);
    };
    @Bean
    public PayStackPaymentService payStackPaymentService(UserService userService, PayStackPaymentOutputPort payStackPaymentOutputPort){
        return new PayStackPaymentService( userService,payStackPaymentOutputPort);
    };
    @Bean
    public UserService userService(UserOutputPort userOutputPort, KeycloakUserService keycloakUserService, PasswordEncoder passwordEncoder){
        return new UserService(userOutputPort,keycloakUserService,passwordEncoder);
    }
    @Bean
    public UserPersistenceAdapter userServicePersistenceAdapter(UserEntityRepo userEntityRepo){
        return new UserPersistenceAdapter(userEntityRepo);
    }
    @Bean
    public PayStackPaymentPersistenceAdapter payStackPaymentPersistenceAdapter(PayStackPaymentEntityRepo payStackPaymentEntityRepo){
        return new PayStackPaymentPersistenceAdapter(payStackPaymentEntityRepo);
    }
    @Bean
    public TransactionPersistenceAdapter transactionPersistenceAdapter(TransactionEntityRepo userEntityRepo){
        return new TransactionPersistenceAdapter(userEntityRepo);
    }
    @Bean
    public WalletPersistenceAdapter walletPersistenceAdapter(WalletEntityRepo walletEntity){
        return new WalletPersistenceAdapter(walletEntity) {
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public KeycloakUserService keycloakUserService(RestTemplate restTemplate, Keycloak keycloak){
        return new KeycloakUserService(restTemplate,keycloak);
    }
    @Bean
    public PremblyIdentityVerificationService premblyIdentityVerificationService(Cloudinary cloudinary){
        return new PremblyIdentityVerificationService(cloudinary);
    }
    @Bean
    public Keycloak keycloak(){
        return  KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType("client_credentials")
                .build();
    }


}
