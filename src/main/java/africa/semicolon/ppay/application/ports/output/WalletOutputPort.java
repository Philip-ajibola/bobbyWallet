package africa.semicolon.ppay.application.ports.output;

import africa.semicolon.ppay.domain.model.Wallet;

public interface WalletOutputPort {
    Wallet saveWallet(Wallet wallet);
    Wallet getWalletById(Long id);

    boolean existById(Long id);
}
