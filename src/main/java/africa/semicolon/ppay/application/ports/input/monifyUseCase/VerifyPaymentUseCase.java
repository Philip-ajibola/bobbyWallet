package africa.semicolon.ppay.application.ports.input.monifyUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.MonifyVerifyTransactionResponse;

public interface VerifyPaymentUseCase {
    MonifyVerifyTransactionResponse verifyTransaction(String reference);
}
