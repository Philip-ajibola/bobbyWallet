package africa.semicolon.ppay.infrastructure.config;

import africa.semicolon.ppay.application.ports.output.PayStackPaymentOutputPort;
import africa.semicolon.ppay.application.ports.output.TransactionOutputPort;
import africa.semicolon.ppay.application.ports.output.UserOutputPort;
import africa.semicolon.ppay.application.ports.output.WalletOutputPort;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.domain.service.PayStackPaymentService;
import africa.semicolon.ppay.domain.service.TransactionService;
import africa.semicolon.ppay.domain.service.UserService;
import africa.semicolon.ppay.domain.service.WalletService;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.PayStackPaymentEntityRepo;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.TransactionEntityRepo;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.UserEntityRepo;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.repository.WalletEntityRepo;
import africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter.PayStackPaymentPersistenceAdapter;
import africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter.TransactionPersistenceAdapter;
import africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter.UserPersistenceAdapter;
import africa.semicolon.ppay.infrastructure.adapter.output.persistenceAdapter.WalletPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean
    public WalletService walletService(WalletOutputPort walletOutputPort,TransactionService transactionService,PayStackPaymentService payStackPaymentService){
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
    public UserService userService(UserOutputPort userOutputPort){
        return new UserService(userOutputPort);
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
}
