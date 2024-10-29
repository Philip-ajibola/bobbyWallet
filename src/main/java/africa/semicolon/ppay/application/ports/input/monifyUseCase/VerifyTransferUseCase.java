package africa.semicolon.ppay.application.ports.input.monifyUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.VerifyTransferResponse;

public interface VerifyTransferUseCase {
    VerifyTransferResponse verifyTransfer(String reference);
}
