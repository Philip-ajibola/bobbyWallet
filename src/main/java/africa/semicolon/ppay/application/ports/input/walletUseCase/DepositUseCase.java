package africa.semicolon.ppay.application.ports.input.walletUseCase;

import africa.semicolon.ppay.infrastructure.adapter.input.dto.request.DepositDto;
import africa.semicolon.ppay.infrastructure.adapter.input.dto.response.InitializePaymentResponse;

public interface DepositUseCase {
    InitializePaymentResponse deposit(DepositDto depositDto);
}
