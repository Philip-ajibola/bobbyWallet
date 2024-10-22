package africa.semicolon.ppay.application.ports.input.paystackUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.InitializePaymentDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.InitializePaymentResponse;

public interface InitializePaymentUseCase {
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);

}
