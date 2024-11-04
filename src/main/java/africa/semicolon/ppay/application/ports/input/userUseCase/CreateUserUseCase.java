package africa.semicolon.ppay.application.ports.input.userUseCase;

import africa.semicolon.ppay.application.ports.input.walletUseCase.CreateWalletUseCase;
import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.domain.service.WalletService;

public interface CreateUserUseCase {
    User createUser(User user, CreateWalletUseCase createWalletUseCase);
}
