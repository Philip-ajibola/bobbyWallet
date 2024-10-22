package africa.semicolon.ppay.application.ports.input.walletUseCase;

import africa.semicolon.ppay.domain.model.Wallet;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.VerifyPaymentDto;

public interface VerifyPaymentUseCase {
    Wallet verifyPayment(VerifyPaymentDto verifyPaymentDto);
}
