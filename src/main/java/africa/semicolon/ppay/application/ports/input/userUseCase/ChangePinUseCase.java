package africa.semicolon.ppay.application.ports.input.userUseCase;

import africa.semicolon.ppay.domain.model.User;
import africa.semicolon.ppay.domain.service.WalletService;

public interface ChangePinUseCase {
    User changePin(Long userId, String password, String newPin, WalletService walletService);
}
