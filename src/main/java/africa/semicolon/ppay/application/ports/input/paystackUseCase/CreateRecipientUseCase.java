package africa.semicolon.ppay.application.ports.input.paystackUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.CreateTransferRecipientDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.CreateTransferRecipientResponse;

public interface CreateRecipientUseCase {
    CreateTransferRecipientResponse createTransferRecipient(CreateTransferRecipientDto createTransferRecipientDto);
}