package africa.semicolon.ppay.application.ports.input.userUseCase;

import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.application.service.WalletService;

public interface CreateUserUseCase {
    User createUser(User user, WalletService walletService);
}
