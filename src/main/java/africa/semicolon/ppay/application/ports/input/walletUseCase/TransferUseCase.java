package africa.semicolon.ppay.application.ports.input.walletUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.TransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.InitializeTransferResponse;

public interface TransferUseCase {
    InitializeTransferResponse transfer(TransferDto transferDto);
}
