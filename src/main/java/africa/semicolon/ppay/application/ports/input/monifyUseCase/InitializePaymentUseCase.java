package africa.semicolon.ppay.application.ports.input.monifyUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.MonifyInitializePaymentRequest;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.MonifyInitializePaymentResponse;

public interface InitializePaymentUseCase {
    MonifyInitializePaymentResponse initializePayment(MonifyInitializePaymentRequest dto);
}
