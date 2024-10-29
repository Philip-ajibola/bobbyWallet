package africa.semicolon.ppay.application.ports.input.monifyUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.AuthorizeRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.AuthorizeTransactionResponse;

public interface AuthorizeTransferUseCase {
    AuthorizeTransactionResponse authorizeTransfer(AuthorizeRequest request);
}
