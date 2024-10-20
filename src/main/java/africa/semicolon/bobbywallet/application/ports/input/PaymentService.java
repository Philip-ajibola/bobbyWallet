package africa.semicolon.bobbywallet.application.ports.input;

import africa.semicolon.bobbywallet.application.dto.request.CreateTransferRecipientDto;
import africa.semicolon.bobbywallet.application.dto.request.InitializePaymentDto;
import africa.semicolon.bobbywallet.application.dto.request.InitializeTransferDto;
import africa.semicolon.bobbywallet.application.dto.response.*;

public interface PaymentService {
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    WalletResponse paymentVerification(String reference, Long id, WalletService repo) throws Exception;
    WalletResponse transferVerification(String reference, Long id, WalletService repo) throws Exception;
    CreateTransferRecipientResponse createTransferRecipient(CreateTransferRecipientDto createTransferRecipientDto);
    InitializeTransferResponse transfer(InitializeTransferDto request);

}
