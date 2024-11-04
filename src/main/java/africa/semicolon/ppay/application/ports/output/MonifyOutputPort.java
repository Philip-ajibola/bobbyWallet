package africa.semicolon.ppay.application.ports.output;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.MonifyInitializePaymentRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.AuthorizeRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.MonifyInitializeTransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.*;

public interface MonifyOutputPort {
    MonifyInitializePaymentResponse initializePayment(MonifyInitializePaymentRequest request);
    MonifyVerifyTransactionResponse verifyTransaction(String reference);
    MonifyInitializeTransferResponse initializeTransfer(MonifyInitializeTransferDto dto);
    AuthorizeTransactionResponse authorizeTransfer(AuthorizeRequest request);
    VerifyTransferResponse verifyTransfer(String reference);
}
