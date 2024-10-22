package africa.semicolon.ppay.application.ports.input.walletUseCase;

import africa.semicolon.ppay.domain.model.Wallet;

public interface ResetPinUseCase {
    Wallet resetPin(Long walletId,String newPin);
}
