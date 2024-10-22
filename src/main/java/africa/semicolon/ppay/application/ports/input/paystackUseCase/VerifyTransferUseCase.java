package africa.semicolon.ppay.application.ports.input.paystackUseCase;

import africa.semicolon.ppay.domain.service.WalletService;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.TransferVerificationResponse;

public interface VerifyTransferUseCase {
    TransferVerificationResponse transferVerification(String reference, Long id) throws Exception;

}
