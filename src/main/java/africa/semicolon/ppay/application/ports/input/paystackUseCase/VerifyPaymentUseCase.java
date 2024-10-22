package africa.semicolon.ppay.application.ports.input.paystackUseCase;

import africa.semicolon.ppay.domain.service.WalletService;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.PaymentVerificationResponse;

public interface  VerifyPaymentUseCase {
    PaymentVerificationResponse paymentVerification(String reference, Long id) throws Exception;

}
