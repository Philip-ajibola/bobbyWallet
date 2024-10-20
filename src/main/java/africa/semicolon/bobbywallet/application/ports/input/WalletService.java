package africa.semicolon.bobbywallet.application.ports.input;

import africa.semicolon.bobbywallet.application.dto.request.*;
import africa.semicolon.bobbywallet.application.dto.response.InitializePaymentResponse;
import africa.semicolon.bobbywallet.application.dto.response.InitializeTransferResponse;
import africa.semicolon.bobbywallet.application.dto.response.WalletResponse;
import africa.semicolon.bobbywallet.domain.model.Wallet;

public interface WalletService {
    Wallet createWallet(Long userId);
    InitializePaymentResponse deposit(InitializePaymentDto request, PaymentService paymentService);
    InitializeTransferResponse transfer(TransferDto request, PaymentService paymentService);
    WalletResponse getBalance(Long id);

    Wallet save(Wallet wallet);

    Wallet getById(Long id);
}
