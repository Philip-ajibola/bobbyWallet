package africa.semicolon.ppay.application.ports.input.walletUseCase;

import africa.semicolon.ppay.domain.model.Wallet;

public interface ChangeWalletPinUseCase {
    Wallet updatePin(Long id, String pin);
}
