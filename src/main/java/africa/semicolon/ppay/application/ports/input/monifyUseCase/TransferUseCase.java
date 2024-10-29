package africa.semicolon.ppay.application.ports.input.monifyUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.MonifyInitializeTransferDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.MonifyInitializeTransferResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TransferUseCase {
    MonifyInitializeTransferResponse initializeTransfer(MonifyInitializeTransferDto dto) throws JsonProcessingException;
}
