package africa.semicolon.ppay.infrastructure.adapter.input.mappers;

import africa.semicolon.ppay.domain.model.Transaction;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.domain.model.Wallet;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.CreateUserDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.TransactionResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.UserResponse;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.WalletResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DtoMappers {
    DtoMappers INSTANCE = Mappers.getMapper(DtoMappers.class);
    User toUser(CreateUserDto  input);

    WalletResponse toWalletResponse(Wallet wallet);
    @Mapping(source = "wallet", target = "wallet")
    UserResponse toUserResponse(User user);

    TransactionResponse toTransactionResponse(Transaction transaction);
}
