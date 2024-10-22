package africa.semicolon.ppay.application.ports.input.paystackUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.InitializeTransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.InitializeTransferResponse;

public interface InitializeTransferUseCase {
    InitializeTransferResponse transfer(InitializeTransferDto request);
}

