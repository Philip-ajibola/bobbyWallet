package africa.semicolon.ppay.infrastructure.adapter.output.mappers;

import africa.semicolon.ppay.domain.model.Payment;
import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.domain.model.Wallet;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.PaymentEntity;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.UserEntity;
import africa.semicolon.ppay.infrastructure.adapter.output.persistence.entities.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityMappers {
    EntityMappers INSTANCE = Mappers.getMapper(EntityMappers.class);
    @Mapping(source = "wallet",target = "wallet")
    User toModel(UserEntity user);
    Wallet toModel(WalletEntity walletEntity);
    Payment toModel(PaymentEntity paymentEntity);
    PaymentEntity toEntity(Payment payment);
    Transaction toModel(TransactionEntity transactionEntity);
    @Mapping(source = "wallet",target = "wallet")
    UserEntity toEntity(User user);
    TransactionEntity toEntity(Transaction transaction);
    WalletEntity toEntity(Wallet wallet);


}
